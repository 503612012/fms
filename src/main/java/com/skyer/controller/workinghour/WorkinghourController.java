package com.skyer.controller.workinghour;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyer.controller.base.BaseController;
import com.skyer.enumerate.ResultEnum;
import com.skyer.service.WorkinghourService;
import com.skyer.vo.Workinghour;
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
 * 工时控制器
 *
 * @author skyer
 */
@Controller
@RequestMapping("/workinghour/workinghour")
public class WorkinghourController extends BaseController {

    private final static Logger L = Logger.getLogger(WorkinghourController.class);

    @Resource
    private WorkinghourService workinghourService;

    /**
     * 去到工时管理主页面
     */
    @RequestMapping("/index")
    @RequiresPermissions("C1_01")
    public ModelAndView index() {
        try {
            ModelAndView mv = new ModelAndView("/view/workinghour/index");
            mv.addObject("date", new DateTime().plusDays(-1).toString("yyyy-MM-dd"));
            return mv;
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询工时列表
     *
     * @param page    当前页码
     * @param rows    每页显示数量
     * @param date    日期
     * @param keyword 搜索关键字
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("C1_01")
    public Object list(Integer page, Integer rows, String date, String keyword, HttpServletRequest req) {
        try {
            JSONArray list = workinghourService.findByPage(page, rows, date, keyword);
            JSONObject result = new JSONObject();
            Long total = workinghourService.total(date, keyword);
            result.put("rows", list);
            result.put("total", total);
            req.setAttribute("keyword", StringUtils.isEmpty(keyword) ? "" : keyword);
            req.setAttribute("date", StringUtils.isEmpty(date) ? "" : date);
            return result;
        } catch (Exception e) {
            L.error("---------------------------入参[page:" + page + ", rows:" + rows + ", date:" + date + ", keyword:" + keyword + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

    /**
     * 去到录入工时页面
     */
    @RequestMapping("/add")
    @RequiresPermissions("C2_02")
    public ModelAndView add() {
        try {
            return new ModelAndView("/view/workinghour/add");
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询是否已经录入
     *
     * @param eid  员工ID
     * @param date 日期
     */
    @RequestMapping("/isInput")
    @ResponseBody
    @RequiresPermissions("C2_02")
    public Object isInput(Integer eid, String date) {
        try {
            return super.success(workinghourService.isInput(eid, date));
        } catch (Exception e) {
            L.error("---------------------------入参[eid:" + eid + ", date:" + date + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

    /**
     * 录入工时
     */
    @RequestMapping("/insert")
    @ResponseBody
    @RequiresPermissions("C2_02")
    public Object add(Workinghour workinghour) {
        try {
            workinghour.setInputId(super.getCurrentUser().getId());
            workinghour.setInputNickName(super.getCurrentUser().getNickName());
            workinghour.setInputDate(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            workinghour.setLastModifyDate(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            workinghour.setLastModifyOperatorId(super.getCurrentUser().getId());
            workinghourService.deleteByEidAndDate(workinghour.getEid(), workinghour.getDate());
            boolean flag = workinghourService.insert(workinghour);
            if (flag) {
                return super.success("录入成功！");
            }
        } catch (Exception e) {
            L.error("---------------------------入参[workinghour: " + workinghour.toString() + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.INSERT_ERROR.getCode(), ResultEnum.INSERT_ERROR.getValue());
    }

}
