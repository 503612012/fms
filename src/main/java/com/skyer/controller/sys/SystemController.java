package com.skyer.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.skyer.constant.Constant;
import com.skyer.controller.base.BaseController;
import com.skyer.enumerate.ResultEnum;
import com.skyer.service.LogService;
import com.skyer.service.MenuService;
import com.skyer.service.UserService;
import com.skyer.util.CaptchaUtil;
import com.skyer.util.IPUtils;
import com.skyer.vo.Log;
import com.skyer.vo.User;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 系统控制器
 *
 * @author skyer
 */
@Controller
@RequestMapping("/sys")
public class SystemController extends BaseController {

    private final static Logger L = Logger.getLogger(SystemController.class);

    @Resource
    private LogService logService;
    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;

    /**
     * 登录操作
     *
     * @param userName 用户名
     * @param password 密码
     * @param captcha  验证码
     */
    @RequestMapping("/login")
    @ResponseBody
    public Object login(User user, HttpServletRequest req) {
        try {
            String captcha = req.getParameter("captcha");
            JSONObject obj = (JSONObject) req.getSession().getAttribute(Constant.CAPTCHA_CODE);
            long createTime = Long.valueOf(obj.getString("createTime")); // 生成验证码的时间
            long currentTime = System.currentTimeMillis();
            if ((currentTime - createTime) > (300000)) { // 验证码超时(单位：毫秒)
                return super.fail(ResultEnum.CAPTCHA_TIMEOUT.getCode(), ResultEnum.CAPTCHA_TIMEOUT.getValue());
            }
            String code = obj.getString("code").toLowerCase(); // session中保存的验证码
            if (!code.equals(captcha.toLowerCase())) { // 验证码输入错误
                return super.fail(ResultEnum.CAPTCHA_WRONG.getCode(), ResultEnum.CAPTCHA_WRONG.getValue());
            }

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
            subject.login(token);

            User userInDb = userService.findByUserName(user.getUserName());
            // 登录成功后放入application，防止同一个账户多人登录
            ServletContext application = req.getServletContext();
            @SuppressWarnings("unchecked")
            Map<String, String> loginedMap = (Map<String, String>) application.getAttribute(Constant.LOGINEDUSERS);
            if (loginedMap == null) {
                loginedMap = new HashMap<>();
                application.setAttribute(Constant.LOGINEDUSERS, loginedMap);
            }
            loginedMap.put(userInDb.getUserName(), req.getSession().getId());

            // 登录成功后放入session中
            req.getSession().setAttribute(Constant.CURRENT_USER, userInDb);
            this.addLog("成功！", IPUtils.getClientIPAddr(req), "登录系统！", userInDb.getId(), userInDb.getUserName());
            return super.success("登录成功！");
        } catch (Exception e) {
            L.error("---------------------------", e);
            User userInDb = userService.findByUserName(user.getUserName());
            if (e instanceof UnknownAccountException) {
                this.addLog("失败[该用户不存在！]", IPUtils.getClientIPAddr(req), "登录系统！", 0, user.getUserName());
                return super.fail(ResultEnum.NO_THIS_USER.getCode(), ResultEnum.NO_THIS_USER.getValue());
            } else if (e instanceof IncorrectCredentialsException) {
                this.addLog("失败[密码错误！]", IPUtils.getClientIPAddr(req), "登录系统！", userInDb.getId(), userInDb.getUserName());
                return super.fail(ResultEnum.PASSWORD_WRONG.getCode(), ResultEnum.PASSWORD_WRONG.getValue());
            } else if (e instanceof LockedAccountException) {
                this.addLog("失败[账号已禁用！]", IPUtils.getClientIPAddr(req), "登录系统！", userInDb.getId(), userInDb.getUserName());
                return super.fail(ResultEnum.USER_DISABLE.getCode(), ResultEnum.USER_DISABLE.getValue());
            }
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

    /**
     * 获取目录树
     *
     * @return 目录树json格式
     */
    @RequestMapping("/getMenu")
    @ResponseBody
    public Object getMenu() {
        try {
            return menuService.getMenu(super.getCurrentUser());
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

    /**
     * 登出操作
     */
    @RequestMapping("/logout")
    @ResponseBody
    public Object logout(HttpServletRequest req) {
        try {
            req.getSession().removeAttribute(Constant.CURRENT_USER);
            req.getSession().getServletContext().removeAttribute(Constant.CURRENT_USER);
            return super.success("登出成功！");
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.HANDLE_ERROR.getCode(), ResultEnum.HANDLE_ERROR.getValue());
    }

    /**
     * 去到登录页面
     */
    @RequestMapping("/loginPage")
    public ModelAndView loginPage(String errorMsg) {
        try {
            ModelAndView mv = new ModelAndView("login");
            mv.addObject("errorMsg", errorMsg);
            return mv;
        } catch (Exception e) {
            L.error("---------------------------入参[errorMsg:" + errorMsg + "]", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生产图形验证码
     */
    @RequestMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String code = CaptchaUtil.createNewCaptcha();
            JSONObject obj = new JSONObject();
            obj.put("code", code);
            obj.put("createTime", System.currentTimeMillis());
            // 将认证码存入SESSION
            req.getSession().setAttribute(Constant.CAPTCHA_CODE, obj);
            // 输出
            final Random random = new Random();
            final int codeCount = code.length();
            resp.setHeader("Pragma", "No-cache");
            resp.setHeader("Cache-Control", "no-cache");
            resp.setDateHeader("Expires", 0);
            final int imgWidth = 65, imgHeight = 30;
            BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setColor(new Color(220, 220, 220));
            g.fillRect(0, 0, imgWidth, imgHeight);
            g.setFont(new Font("黑", Font.ITALIC | Font.BOLD, 12));
            for (int i = 0; i < 50; i++) {
                g.setColor(getRandColor());
                int x = random.nextInt(imgWidth);
                int y = random.nextInt(imgHeight);
                g.drawLine(x, y, x, y);
            }
            g.setFont(new Font("黑", Font.ITALIC | Font.BOLD, 24));
            g.setColor(new Color(0, 0, 0));
            for (int i = 0; i < codeCount; i++) {
                g.drawString(code.substring(i, i + 1), 15 * i, 26);
            }
            g.dispose();
            ImageIO.write(image, "JPEG", resp.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取颜色
     */
    private Color getRandColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r, g, b);
    }

    /**
     * 添加日志
     *
     * @param content  日志内容
     * @param ip       操作者IP
     * @param title    日志标题
     * @param userId   操作用户ID
     * @param nickName 操作用户用户名
     */
    private void addLog(String content, String ip, String title, int userId, String nickName) {
        Log log = new Log();
        log.setContent(content);
        log.setCreateTimeTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        log.setIp(ip);
        log.setTitle(title);
        log.setUserId(userId);
        log.setNickName(nickName);
        logService.insert(log);
    }

}
