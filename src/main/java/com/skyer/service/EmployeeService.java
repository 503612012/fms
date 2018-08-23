package com.skyer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyer.mapper.EmployeeMapper;
import com.skyer.util.PageUtil;
import com.skyer.vo.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工服务层
 *
 * @author skyer
 */
@Service
public class EmployeeService extends BaseService {

    @Resource
    private EmployeeMapper employeeMapper;

    /**
     * 添加员工
     */
    @Transactional
    public void insert(Employee employee) {
        super.addLog(employee.toString(), super.getCurrentUserIp(), "添加员工", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        employeeMapper.insert(employee);
    }

    /**
     * 更新
     */
    @Transactional
    public void update(Employee employee) {
        Employee employeeInDb = this.findById(employee.getId());
        StringBuilder sb = new StringBuilder();
        if (!employee.getName().equals(employeeInDb.getName())) {
            sb.append("姓名由[").append(employeeInDb.getName()).append("]改为[").append(employee.getName()).append("]；");
        }
        if (!employee.getAge().equals(employeeInDb.getAge())) {
            sb.append("年龄由[").append(employeeInDb.getAge()).append("]改为[").append(employee.getAge()).append("]；");
        }
        if (!employee.getGender().equals(employeeInDb.getGender())) {
            sb.append("性别由[").append(employeeInDb.getGender() == 1 ? "男" : "女").append("]改为[").append(employee.getGender() == 1 ? "男" : "女").append("]；");
        }
        if (!employee.getContact().equals(employeeInDb.getContact())) {
            sb.append("联系方式由[").append(employeeInDb.getContact()).append("]改为[").append(employee.getContact()).append("]；");
        }
        if (!employee.getDaySalary().equals(employeeInDb.getDaySalary())) {
            sb.append("日薪由[").append(employeeInDb.getDaySalary()).append("]改为[").append(employee.getDaySalary()).append("]；");
        }
        if (!employee.getMonthSalary().equals(employeeInDb.getMonthSalary())) {
            sb.append("月薪由[").append(employeeInDb.getMonthSalary()).append("]改为[").append(employee.getMonthSalary()).append("]；");
        }
        if (!employee.getStatus().equals(employeeInDb.getStatus())) {
            sb.append("状态由[").append(employeeInDb.getStatus() == 1 ? "正常" : "禁用").append("]改为[").append(employee.getStatus() == 1 ? "正常" : "禁用").append("]；");
        }
        String content = "";
        if (!StringUtils.isEmpty(sb.toString())) {
            content = sb.toString().substring(0, sb.toString().length() - 1);
        }
        super.addLog(content, super.getCurrentUserIp(), "修改员工", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        employeeMapper.update(employee);
    }

    /**
     * 通过主键查询
     */
    public Employee findById(Integer id) {
        return employeeMapper.findById(id);
    }

    /**
     * 分页查询员工
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @param keyword  查询关键字
     */
    public JSONArray findByPage(Integer pageNum, Integer pageSize, String keyword) {
        PageUtil pu = new PageUtil(pageNum, pageSize);
        List<Employee> list = employeeMapper.findByPage(pu, keyword);
        return JSON.parseObject(JSONArray.toJSONString(list), JSONArray.class);
    }

    /**
     * 统计员工总数量
     */
    public Long total(String keyword) {
        return employeeMapper.total(keyword);
    }

    /**
     * 删除员工
     */
    @Transactional
    public void delete(Integer id) {
        Employee employee = this.findById(id);
        super.addLog(employee.toString(), super.getCurrentUserIp(), "删除员工", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        employeeMapper.delete(id);
    }

    /**
     * 查询所有员工
     */
    public JSONArray findAll() {
        List<Employee> list = employeeMapper.findAll();
        JSONArray result = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("id", list.get(i).getId());
            obj.put("text", list.get(i).getName());
            if (i == 0) {
                obj.put("selected", true);
            }
            result.add(obj);
        }
        return result;
    }

    /**
     * 获取某员工的日薪
     *
     * @param eid 员工ID
     */
    public double getDaySalaryById(Integer eid) {
        return employeeMapper.getDaySalaryById(eid);
    }

}
