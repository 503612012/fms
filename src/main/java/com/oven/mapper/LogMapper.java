package com.oven.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oven.util.PageUtil;
import com.oven.vo.Log;

/**
 * LogMapper接口
 *
 * @author Oven
 */
public interface LogMapper {

    /**
     * 添加
     */
    Integer insert(Log log);

    /**
     * 分页查询日志
     *
     * @param pu 分页实体类
     */
    List<Log> findByPage(@Param("pu") PageUtil pu, @Param("keyword") String keyword);

    /**
     * 统计日志总数量
     */
    Long total(@Param("keyword") String keyword);

}
