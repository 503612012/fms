package com.skyer.mapper;

import com.skyer.util.PageUtil;
import com.skyer.vo.Workinghour;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * WorkinghourMapper接口
 *
 * @author skyer
 */
public interface WorkinghourMapper {

    /**
     * 分页查询工时
     *
     * @param pu 分页实体类
     */
    List<Workinghour> findByPage(@Param("pu") PageUtil pu, @Param("date") String date, @Param("keyword") String keyword);

    /**
     * 统计工时总数量
     */
    Long total(@Param("date") String date, @Param("keyword") String keyword);

    /**
     * 添加
     */
    int insert(Workinghour workinghour);

    /**
     * 查询是否已经录入
     */
    long isInput(@Param("eid") Integer eid, @Param("date") String date);

    /**
     * 根据员工ID和日期删除
     */
    void deleteByEidAndDate(@Param("eid") Integer eid, @Param("date") String datee);

    /**
     * 通过员工ID和日期查询
     */
    List<Workinghour> findByEidAndDate(@Param("dateType") String dateType, @Param("date") String date, @Param("eid") Integer eid);

}
