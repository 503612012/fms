package com.oven.controller.salary;

import com.oven.controller.base.BaseController;
import com.oven.enumerate.ResultEnum;
import com.oven.service.SalaryService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 工资控制器
 *
 * @author Oven
 */
@Controller
@RequestMapping("/salary/salary")
public class SalaryController extends BaseController {

    private final static Logger L = Logger.getLogger(SalaryController.class);

    @Resource
    private SalaryService salaryService;

    /**
     * 去到工资统计主页面
     */
    @RequestMapping("/index")
    @RequiresPermissions("E1_01")
    public ModelAndView index() {
        try {
            return new ModelAndView("/view/salary/index");
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取表格数据
     *
     * @param dateType 日期类型
     * @param date     日期
     * @param eid      员工ID
     */
    @RequestMapping("/getTableData")
    @ResponseBody
    @RequiresPermissions("E1_01")
    public Object getTableData(String dateType, String date, Integer eid) {
        try {
            return salaryService.countSalary(dateType, date, eid);
        } catch (Exception e) {
            L.error("---------------------------入参[dateType:" + dateType + ", date:" + date + ", eid:" + eid + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

    /**
     * 获取图表数据
     *
     * @param dateType 日期类型
     * @param date     日期
     * @param eid      员工ID
     */
    @RequestMapping("/getChartsData")
    @ResponseBody
    @RequiresPermissions("E1_01")
    public Object getChartsData(String dateType, String date, Integer eid) {
        try {
            return super.success(salaryService.getChartsData(dateType, date, eid));
        } catch (Exception e) {
            L.error("---------------------------入参[dateType:" + dateType + ", date:" + date + ", eid:" + eid + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

}
