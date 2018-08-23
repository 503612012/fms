package com.skyer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.skyer.vo.Menu;

/**
 * MenuMapper接口
 *
 * @author skyer
 */
public interface MenuMapper {

    /**
     * 添加
     */
    void insert(Menu menu);

    /**
     * 修改
     */
    void update(Menu menu);

    /**
     * 根据用户ID获取一个目录的所有子目录
     *
     * @param pid    父ID
     * @param userId 用户ID
     */
    List<Menu> getChildrenByPid(@Param("pid") Integer pid, @Param("userId") Integer userId);

    /**
     * 通过主键获取
     */
    Menu findById(Integer id);

    /**
     * 分页获取菜单
     *
     * @param keyword 关键字
     */
    List<Menu> findByPage(@Param("keyword") String keyword);

    /**
     * 统计菜单总数量
     *
     * @param keyword 关键字
     */
    Long total(@Param("keyword") String keyword);

    /**
     * 通过菜单编号获取
     *
     * @param menuCode 菜单编号
     */
    Menu findByMenuCode(@Param("menuCode") String menuCode);

    /**
     * 获取一个目录的所有子目录
     *
     * @param pid 父ID
     */
    List<Menu> getMemuByPid(@Param("pid") Integer pid);

}
