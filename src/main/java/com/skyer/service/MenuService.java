package com.skyer.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyer.mapper.MenuMapper;
import com.skyer.vo.Menu;
import com.skyer.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单服务层
 *
 * @author skyer
 */
@Service
public class MenuService extends BaseService {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private UserPermissionService userPermissionService;

    /**
     * 添加
     */
    @Transactional
    public void insert(Menu menu) {
        super.addLog(menu.toString(), super.getCurrentUserIp(), "添加目录", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        menuMapper.insert(menu);
    }

    /**
     * 修改
     */
    @Transactional
    public void update(Menu menu) {
        Menu menuInDb = this.findById(menu.getId());
        StringBuilder sb = new StringBuilder();
        if (!menuInDb.getMenuName().equals(menu.getMenuName())) {
            sb.append("名称由[").append(menuInDb.getMenuName()).append("]改为[").append(menu.getMenuName()).append("]；");
        }
        if (!menuInDb.getSort().equals(menu.getSort())) {
            sb.append("排序值由[").append(menuInDb.getSort()).append("]改为[").append(menu.getSort()).append("]；");
        }
        if (!menuInDb.getStatus().equals(menu.getStatus())) {
            sb.append("状态由[").append(menuInDb.getStatus() == 1 ? "正常" : "禁用").append("]改为[").append(menu.getStatus() == 1 ? "正常" : "禁用").append("]；");
        }
        String content = "";
        if (!StringUtils.isEmpty(sb.toString())) {
            content = sb.toString().substring(0, sb.toString().length() - 1);
        }
        super.addLog(content, super.getCurrentUserIp(), "修改目录", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        menuMapper.update(menu);
    }

    /**
     * 通过主键获取
     */
    public Menu findById(Integer id) {
        return menuMapper.findById(id);
    }

    /**
     * 获取目录树
     *
     * @param currentUser 当前登录用户对象
     */
    public JSONArray getMenu(User currentUser) {
        JSONArray result = new JSONArray(); // 最终返回结果
        List<Menu> levelOneMenus = menuMapper.getChildrenByPid(0, currentUser.getId()); // 所有的一级目录
        for (Menu levelOneMenu : levelOneMenus) { // 遍历所有的一级目录
            JSONObject levelOneItem = new JSONObject(); // 一级目录的元素项
            levelOneItem.put("id", levelOneMenu.getMenuCode());
            levelOneItem.put("text", levelOneMenu.getMenuName());
            levelOneItem.put("iconCls", levelOneMenu.getIconCls());
            JSONArray levelOneChildrens = new JSONArray(); // 一级目录的子目录
            List<Menu> levelTwoMenus = menuMapper.getChildrenByPid(levelOneMenu.getId(), currentUser.getId()); // 获取该一级目录的所有子目录
            if (levelTwoMenus.size() == 0) {
                continue;
            }
            levelOneItem.put("state", "closed");
            for (Menu levelTwoMenu : levelTwoMenus) { // 遍历所有的二级目录
                JSONObject levelTwoItem = new JSONObject(); // 二级目录的元素项
                levelTwoItem.put("id", levelTwoMenu.getMenuCode());
                levelTwoItem.put("text", levelTwoMenu.getMenuName());
                levelTwoItem.put("iconCls", levelTwoMenu.getIconCls());
                JSONObject levelTwoAttr = new JSONObject();
                levelTwoAttr.put("url", levelTwoMenu.getUrl());
                levelTwoItem.put("attributes", levelTwoAttr);
                levelOneChildrens.add(levelTwoItem);
            }
            levelOneItem.put("children", levelOneChildrens);
            result.add(levelOneItem);
        }
        return result;
    }

    /**
     * 分页查询菜单数据
     *
     * @param keyword 关键字
     */
    public JSONArray findByPage(String keyword) {
        List<Menu> list = menuMapper.findByPage(keyword);
        JSONArray arr = new JSONArray();
        for (Menu item : list) {
            JSONObject obj = new JSONObject();
            obj.put("id", item.getId());
            obj.put("name", item.getMenuName());
            obj.put("iconCls", item.getIconCls());
            obj.put("menuCode", item.getMenuCode());
            obj.put("modifyTime", item.getModifyTime());
            obj.put("type", item.getType());
            obj.put("status", item.getStatus());
            if (item.getPid() == 0) {
                obj.put("state", "closed");
            } else {
                obj.put("_parentId", item.getPid());
            }
            arr.add(obj);
        }
        return arr;
    }

    /**
     * 统计菜单总数量
     *
     * @param keyword 关键字
     */
    public Long total(String keyword) {
        return menuMapper.total(keyword);
    }

    /**
     * 通过菜单编号获取
     *
     * @param menuCode 菜单编号
     */
    public Menu findByMenuCode(String menuCode) {
        return menuMapper.findByMenuCode(menuCode);
    }

    /**
     * 获取菜单树
     *
     * @param userId 用户ID
     */
    public JSONArray getMenuTree(Integer userId) {
        JSONArray result = new JSONArray(); // 最终返回结果
        List<Menu> levelOneMenus = menuMapper.getMemuByPid(0); // 所有的一级目录
        for (Menu levelOneMenu : levelOneMenus) { // 遍历所有的一级目录
            JSONObject levelOneItem = new JSONObject(); // 一级目录的元素项
            levelOneItem.put("id", levelOneMenu.getMenuCode());
            levelOneItem.put("text", levelOneMenu.getMenuName());
            levelOneItem.put("checked", hasPermission(userId, levelOneMenu.getId()));
            levelOneItem.put("iconCls", levelOneMenu.getIconCls());
            JSONArray levelOneChildrens = new JSONArray(); // 一级目录的子目录
            List<Menu> levelTwoMenus = menuMapper.getMemuByPid(levelOneMenu.getId()); // 获取该一级目录的所有子目录
            if (levelTwoMenus.size() == 0) {
                continue;
            }
            levelOneItem.put("state", "closed");
            for (Menu levelTwoMenu : levelTwoMenus) { // 遍历所有的二级目录
                JSONObject levelTwoItem = new JSONObject(); // 二级目录的元素项
                levelTwoItem.put("id", levelTwoMenu.getMenuCode());
                levelTwoItem.put("text", levelTwoMenu.getMenuName());
                levelTwoItem.put("checked", hasPermission(userId, levelTwoMenu.getId()));
                levelTwoItem.put("iconCls", levelTwoMenu.getIconCls());
                levelOneChildrens.add(levelTwoItem);

                JSONArray levelTwoChildrens = new JSONArray(); // 二级目录的子目录
                List<Menu> levelThreeMenus = menuMapper.getMemuByPid(levelTwoMenu.getId()); // 获取该二级目录的所有子目录
                if (levelThreeMenus.size() == 0) {
                    continue;
                }
                levelTwoItem.put("state", "closed");
                for (Menu levelThreeMenu : levelThreeMenus) { // 遍历所有的三级目录
                    JSONObject levelThreeItem = new JSONObject(); // 三级目录的元素项
                    levelThreeItem.put("id", levelThreeMenu.getMenuCode());
                    levelThreeItem.put("text", levelThreeMenu.getMenuName());
                    levelThreeItem.put("checked", hasPermission(userId, levelThreeMenu.getId()));
                    levelThreeItem.put("iconCls", levelThreeMenu.getIconCls());
                    levelTwoChildrens.add(levelThreeItem);

                    JSONArray levelThreeChildrens = new JSONArray(); // 三级目录的子目录
                    List<Menu> levelFourMenus = menuMapper.getMemuByPid(levelThreeMenu.getId()); // 获取该三级目录的所有子目录
                    if (levelFourMenus.size() == 0) {
                        continue;
                    }
                    levelThreeItem.put("state", "closed");
                    for (Menu levelFourMenu : levelFourMenus) { // 遍历所有的四级目录
                        JSONObject levelFourItem = new JSONObject(); // 四级目录的元素项
                        levelFourItem.put("id", levelFourMenu.getMenuCode());
                        levelFourItem.put("text", levelFourMenu.getMenuName());
                        levelFourItem.put("checked", hasPermission(userId, levelFourMenu.getId()));
                        levelFourItem.put("iconCls", levelFourMenu.getIconCls());
                        levelThreeChildrens.add(levelFourItem);
                    }
                    levelThreeItem.put("children", levelThreeChildrens);
                }
                levelTwoItem.put("children", levelTwoChildrens);
            }
            levelOneItem.put("children", levelOneChildrens);
            result.add(levelOneItem);
        }
        return result;
    }

    /**
     * 判断一个用户有没有某个权限
     *
     * @param userId 用户ID
     * @param menuId 目录ID
     */
    private boolean hasPermission(Integer userId, Integer menuId) {
        long num = userPermissionService.hasPermission(userId, menuId);
        return num > 0;
    }

}
