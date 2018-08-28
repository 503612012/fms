package com.skyer.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyer.controller.base.BaseController;
import com.skyer.enumerate.ResultEnum;
import com.skyer.service.MenuService;
import com.skyer.vo.Menu;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 菜单控制器
 *
 * @author skyer
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {

    private final static Logger L = Logger.getLogger(MenuController.class);

    @Resource
    private MenuService menuService;

    /**
     * 去到菜单管理主页面
     */
    @RequestMapping("/index")
    @RequiresPermissions("A1_02")
    public ModelAndView index() {
        try {
            return new ModelAndView("/view/sys/menu/index");
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询菜单列表
     *
     * @param keyword 搜索关键字
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("A1_02")
    public Object list(String keyword, HttpServletRequest req) {
        try {
            JSONArray list = menuService.findByPage(keyword);
            Long total = menuService.total(keyword);
            JSONObject result = new JSONObject();
            result.put("rows", list);
            result.put("total", total);
            req.setAttribute("keyword", StringUtils.isEmpty(keyword) ? "" : keyword);
            return result;
        } catch (Exception e) {
            L.error("---------------------------入参[keyword: " + keyword + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

    /**
     * 更改菜单状态
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    @RequiresPermissions("A1_02_02")
    public Object updateStatus(String menuCode, Integer status) {
        try {
            Menu menu = menuService.findByMenuCode(menuCode);
            menu.setStatus(status);
            menu.setModifyTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            menuService.update(menu);
            return super.success("修改成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[menuCode:" + menuCode + ", status:" + status + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.UPDATE_ERROR.getCode(), ResultEnum.UPDATE_ERROR.getValue());
    }

    /**
     * 修改菜单
     */
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("A1_02_01")
    public Object update(Menu menu) {
        try {
            Menu menuInDb = menuService.findById(menu.getId());
            menuInDb.setMenuName(menu.getMenuName());
            menuInDb.setModifyTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            menuService.update(menuInDb);
            return super.success("修改成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[" + menu.toString() + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.UPDATE_ERROR.getCode(), ResultEnum.UPDATE_ERROR.getValue());
    }

    /**
     * 获取菜单树
     *
     * @param userId 用户ID
     */
    @RequestMapping("/getMenuTree")
    @ResponseBody
    @RequiresPermissions("A1_01_05")
    public Object getMenuTree(Integer userId) {
        try {
            return menuService.getMenuTree(userId);
        } catch (Exception e) {
            L.error("---------------------------入参[userId: " + userId + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

}
