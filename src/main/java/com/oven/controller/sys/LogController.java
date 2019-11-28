package com.oven.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oven.controller.base.BaseController;
import com.oven.enumerate.ResultEnum;
import com.oven.service.LogService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 日志控制器
 *
 * @author Oven
 */
@Controller
@RequestMapping("/sys/log")
public class LogController extends BaseController {

    private final static Logger L = Logger.getLogger(LogController.class);

    @Resource
    private LogService logService;

    /**
     * 去到系统日志主页面
     */
    @RequestMapping("/index")
    @RequiresPermissions("A1_03")
    public ModelAndView index() {
        try {
            return new ModelAndView("/view/sys/log/index");
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询日志列表
     *
     * @param page    当前页码
     * @param rows    每页显示数量
     * @param keyword 搜索关键字
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("A1_03")
    public Object list(Integer page, Integer rows, String keyword, HttpServletRequest req) {
        try {
            JSONArray list = logService.findByPage(page, rows, keyword);
            JSONObject result = new JSONObject();
            Long total = logService.total(keyword);
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

}
