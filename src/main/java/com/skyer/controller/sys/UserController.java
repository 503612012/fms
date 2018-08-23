package com.skyer.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyer.constant.Constant;
import com.skyer.controller.base.BaseController;
import com.skyer.enumerate.ResultEnum;
import com.skyer.service.MenuService;
import com.skyer.service.UserPermissionService;
import com.skyer.service.UserService;
import com.skyer.vo.Menu;
import com.skyer.vo.User;
import com.skyer.vo.UserPermission;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 *
 * @author skyer
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    private final static Logger L = Logger.getLogger(UserController.class);

    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;
    @Resource
    private UserPermissionService userPermissionService;

    /**
     * 去到用户管理主页面
     */
    @RequestMapping("/index")
    @RequiresPermissions("A1_01")
    public ModelAndView index() {
        try {
            return new ModelAndView("/view/sys/user/index");
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询用户列表
     *
     * @param page    当前页码
     * @param rows    每页显示数量
     * @param keyword 搜索关键字
     */
    @RequestMapping("/list")
    @RequiresPermissions("A1_01")
    @ResponseBody
    public Object list(Integer page, Integer rows, String keyword, HttpServletRequest req) {
        try {
            JSONArray list = userService.findByPage(page, rows, keyword);
            JSONObject result = new JSONObject();
            Long total = userService.total(keyword);
            result.put("rows", list);
            result.put("total", total);
            req.setAttribute("keyword", StringUtils.isEmpty(keyword) ? "" : keyword);
            return result;
        } catch (Exception e) {
            L.error("---------------------------入参[page:" + page + ", rows:" + rows + ", keyword:" + keyword + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

    /**
     * 更改用户状态
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    @RequiresPermissions("A1_01_04")
    public Object updateStatus(Integer id, Integer status) {
        try {
            User user = userService.findById(id);
            user.setStatus(status);
            user.setModifyTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            userService.update(user);
            return super.success("修改成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[id:" + id + ", status:" + status + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.UPDATE_ERROR.getCode(), ResultEnum.UPDATE_ERROR.getValue());
    }

    /**
     * 添加用户
     */
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("A1_01_01")
    public Object add(User user) {
        try {
            User userInDb = userService.findByUserName(user.getUserName());
            if (userInDb != null) {
                return super.fail(ResultEnum.USER_ALREADY_EXIST.getCode(), ResultEnum.USER_ALREADY_EXIST.getValue());
            }
            user.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            user.setModifyTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            user.setStatus(1);
            Md5Hash md5 = new Md5Hash(user.getPassword(), Constant.MD5_SALT);
            user.setPassword(md5.toString());
            userService.insert(user);
            return super.success("添加成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[" + user.toString() + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.INSERT_ERROR.getCode(), ResultEnum.INSERT_ERROR.getValue());
    }

    /**
     * 修改用户
     */
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("A1_01_02")
    public Object update(User user) {
        try {
            User userInDb = userService.findById(user.getId());
            userInDb.setContact(user.getContact());
            userInDb.setNickName(user.getNickName());
            Md5Hash md5 = new Md5Hash(user.getPassword(), Constant.MD5_SALT);
            userInDb.setPassword(md5.toString());
            userInDb.setModifyTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            userService.update(userInDb);
            return super.success("修改成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[" + user.toString() + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.UPDATE_ERROR.getCode(), ResultEnum.UPDATE_ERROR.getValue());
    }

    /**
     * 删除用户
     */
    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions("A1_01_03")
    public Object delete(Integer id) {
        try {
            userService.delete(id);
            return super.success("删除成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[id:" + id + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.DELETE_ERROR.getCode(), ResultEnum.DELETE_ERROR.getValue());
    }

    /**
     * 设置权限
     *
     * @param data   目录编码的集合以及是否选中
     * @param userId 用户ID
     */
    @RequestMapping("/setPermission")
    @ResponseBody
    public Object setPermission(String data, Integer userId) {
        try {
            JSONArray arr = JSONArray.parseArray(data);
            // 删除此人所有权限
            userPermissionService.deleteUserPermission(userId);
            for (int i = 0; i < arr.size(); i++) {
                if ("false".equals(arr.getJSONObject(i).getString("checked"))) {
                    continue;
                }
                Menu menu = menuService.findByMenuCode(arr.getJSONObject(i).getString("menuCode"));
                UserPermission up = new UserPermission();
                up.setMenuId(menu.getId());
                up.setUserId(userId);
                userPermissionService.insert(up);
            }
            return super.success("设置成功！");
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.UPDATE_ERROR.getCode(), ResultEnum.UPDATE_ERROR.getValue());
    }

}
