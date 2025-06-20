package com.oven.fms.core.employee.dao;

import com.oven.basic.base.dao.BaseDao;
import com.oven.basic.base.entity.ConditionAndParams;
import com.oven.fms.core.employee.entity.Employee;
import org.springframework.stereotype.Repository;

/**
 * 员工dao层
 *
 * @author Oven
 */
@Repository
public class EmployeeDao extends BaseDao<Employee> {

    /**
     * 获取一个员工的时薪
     */
    public Double getHourSalaryByEmployeeId(Integer employeeId) {
        return super.getOneDouble(ConditionAndParams.build("dbid", employeeId), "hour_salary");
    }

}
