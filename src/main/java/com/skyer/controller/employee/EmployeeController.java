package com.skyer.controller.employee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyer.controller.base.BaseController;
import com.skyer.enumerate.ResultEnum;
import com.skyer.service.EmployeeService;
import com.skyer.vo.Employee;
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
 * 员工控制器
 *
 * @author skyer
 */
@Controller
@RequestMapping("/employee/employee")
public class EmployeeController extends BaseController {

    private final static Logger L = Logger.getLogger(EmployeeController.class);

    @Resource
    private EmployeeService employeeService;

    /**
     * 去到员工管理主页面
     */
    @RequestMapping("/index")
    @RequiresPermissions("B1_01")
    public ModelAndView index() {
        try {
            return new ModelAndView("/view/employee/employee/index");
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询员工列表
     *
     * @param page    当前页码
     * @param rows    每页显示数量
     * @param keyword 搜索关键字
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("B1_01")
    public Object list(Integer page, Integer rows, String keyword, HttpServletRequest req) {
        try {
            JSONArray list = employeeService.findByPage(page, rows, keyword);
            JSONObject result = new JSONObject();
            Long total = employeeService.total(keyword);
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
     * 更改员工状态
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    @RequiresPermissions("B1_01_04")
    public Object updateStatus(Integer id, Integer status) {
        try {
            Employee employee = employeeService.findById(id);
            employee.setStatus(status);
            employee.setModifyTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            employeeService.update(employee);
            return super.success("修改成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[id:" + id + ", status:" + status + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.UPDATE_ERROR.getCode(), ResultEnum.UPDATE_ERROR.getValue());
    }

    /**
     * 添加员工
     */
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("B1_01_01")
    public Object add(Employee employee) {
        try {
            employee.setCreateTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            employee.setModifyTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            employee.setStatus(1);
            employeeService.insert(employee);
            return super.success("添加成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[" + employee.toString() + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.INSERT_ERROR.getCode(), ResultEnum.INSERT_ERROR.getValue());
    }

    /**
     * 修改员工
     */
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("B1_01_02")
    public Object update(Employee employee) {
        try {
            Employee employeeInDb = employeeService.findById(employee.getId());
            employee.setModifyTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            employee.setCreateTime(employeeInDb.getCreateTime());
            employee.setStatus(employeeInDb.getStatus());
            employeeService.update(employee);
            return super.success("修改成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[" + employee.toString() + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.UPDATE_ERROR.getCode(), ResultEnum.UPDATE_ERROR.getValue());
    }

    /**
     * 删除员工
     */
    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions("B1_01_03")
    public Object delete(Integer id) {
        try {
            employeeService.delete(id);
            return super.success("删除成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[id:" + id + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.DELETE_ERROR.getCode(), ResultEnum.DELETE_ERROR.getValue());
    }

    /**
     * 查询所有员工
     */
    @RequestMapping("/findAll")
    @ResponseBody
    @RequiresPermissions("C1_01")
    public Object findAll() {
        try {
            return employeeService.findAll();
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

    /**
     * 查询所有员工没有默认选中值
     */
    @RequestMapping("/findAllWithNoDefault")
    @ResponseBody
    @RequiresPermissions("C1_01")
    public Object findAllWithNoDefault() {
        try {
            return employeeService.findAllWithNoDefault();
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

    /**
     * 获取某员工的日薪
     *
     * @param eid 员工ID
     */
    @RequestMapping("/getDaySalaryById")
    @ResponseBody
    @RequiresPermissions("C2_02")
    public Object getDaySalaryById(Integer eid) {
        try {
            return super.success(employeeService.getDaySalaryById(eid));
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

}
