package com.oven.mapper;

import com.oven.util.PageUtil;
import com.oven.vo.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * EmployeeMapper接口
 *
 * @author Oven
 */
public interface EmployeeMapper {

    /**
     * 添加员工
     */
    void insert(Employee employee);

    /**
     * 更新
     */
    void update(Employee employeeInDb);

    /**
     * 通过主键查询
     */
    Employee findById(Integer id);

    /**
     * 分页查询员工
     *
     * @param pu 分页实体类
     */
    List<Employee> findByPage(@Param("pu") PageUtil pu, @Param("keyword") String keyword);

    /**
     * 统计员工总数量
     */
    Long total(@Param("keyword") String keyword);

    /**
     * 删除员工
     */
    void delete(Integer id);

    /**
     * 查询所有员工
     */
    List<Employee> findAll();

    /**
     * 获取某员工的日薪
     */
    double getDaySalaryById(Integer eid);

}
