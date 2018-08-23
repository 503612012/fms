package com.skyer.mapper;

import com.skyer.util.PageUtil;
import com.skyer.vo.Worksite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * WorksiteMapper
 *
 * @author skyer
 */
public interface WorksiteMapper {

    /**
     * 分页查询工地
     *
     * @param pu 分页实体类
     */
    List<Worksite> findByPage(@Param("pu") PageUtil pu, @Param("keyword") String keyword);

    /**
     * 统计工地总数量
     */
    Long total(@Param("keyword") String keyword);

    /**
     * 添加
     */
    int insert(Worksite worksite);

    /**
     * 更新
     */
    void update(Worksite worksite);

    /**
     * 删除
     */
    int delete(Integer id);

    /**
     * 通过主键查询
     */
    Worksite findById(Integer id);

    /**
     * 查询所有工地
     */
    List<Worksite> findAll();

}
