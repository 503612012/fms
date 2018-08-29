package com.skyer.controller.paySalary;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyer.controller.base.BaseController;
import com.skyer.enumerate.ResultEnum;
import com.skyer.service.PaySalaryService;
import com.skyer.vo.PaySalary;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 工资发放记录控制器
 *
 * @author skyer
 */
@Controller
@RequestMapping("/paySalary/paySalary")
public class PaySalaryController extends BaseController {

    private final static Logger L = Logger.getLogger(PaySalaryController.class);

    @Resource
    private PaySalaryService paySalaryService;

    /**
     * 去到发薪记录主页面
     */
    @RequestMapping("/index")
    @RequiresPermissions("E1_02")
    public ModelAndView index() {
        try {
            return new ModelAndView("/view/paySalary/paySalary/index");
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询发薪记录列表
     *
     * @param page 当前页码
     * @param rows 每页显示数量
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("B1_01")
    public Object list(Integer page, Integer rows, String workDate, String payDate, Integer eid) {
        try {
            JSONArray list = paySalaryService.findByPage(page, rows, workDate, payDate, eid);
            JSONObject result = new JSONObject();
            Long total = paySalaryService.total(workDate, payDate, eid);
            result.put("rows", list);
            result.put("total", total);
            return result;
        } catch (Exception e) {
            L.error("---------------------------入参[page:" + page + ", rows:" + rows + ", workDate:" + workDate + ", payDate:" + payDate + ", eid:" + eid + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

    /**
     * 添加工资发放记录
     */
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("E1_01_01")
    public Object add(PaySalary paySalary) {
        try {
            paySalary.setPayDate(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
            paySalary.setPayId(super.getCurrentUser().getId());
            paySalaryService.insert(paySalary);
            return super.success("工资发放成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[" + paySalary.toString() + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.INSERT_ERROR.getCode(), ResultEnum.INSERT_ERROR.getValue());
    }

}
