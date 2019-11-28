package com.oven.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oven.util.PageUtil;
import com.oven.vo.User;

/**
 * UserMapper接口
 *
 * @author Oven
 */
public interface UserMapper {

    /**
     * 添加用户
     */
    void insert(User user);

    /**
     * 更新
     */
    void update(User user);

    /**
     * 通过主键查询
     */
    User findById(Integer id);

    /**
     * 通过用户名查询
     */
    User findByUserName(String userName);

    /**
     * 分页查询用户
     *
     * @param pu 分页实体类
     */
    List<User> findByPage(@Param("pu") PageUtil pu, @Param("keyword") String keyword);

    /**
     * 统计用户总数量
     */
    Long total(@Param("keyword") String keyword);

    /**
     * 删除用户
     */
    void delete(Integer id);

}
