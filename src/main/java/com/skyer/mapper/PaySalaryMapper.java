package com.skyer.mapper;

import com.skyer.util.PageUtil;
import com.skyer.vo.PaySalary;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * PaySalaryMapper
 *
 * @author skyer
 */
public interface PaySalaryMapper {

    /**
     * 添加工资发放记录
     */
    void insert(PaySalary paySalary);

    /**
     * 分页查询工资发放记录
     *
     * @param pu 分页实体类
     */
    List<Map<String, Object>> findByPage(@Param("pu") PageUtil pu, @Param("workDate") String workDate, @Param("payDate") String payDate, @Param("eid") Integer eid);

    /**
     * 统计工资发放记录总数量
     */
    Long total(@Param("workDate") String workDate, @Param("payDate") String payDate, @Param("eid") Integer eid);

    /**
     * 条件查询
     */
    PaySalary findByEidAndWorkDate(@Param("eid") Integer eid, @Param("date") String date);

}
