package com.oven.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.oven.mapper.UserMapper;
import com.oven.util.PageUtil;
import com.oven.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 用户服务层
 *
 * @author Oven
 */
@Service
public class UserService extends BaseService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserPermissionService userPermissionService;

    /**
     * 添加用户
     */
    @Transactional
    public void insert(User user) {
        super.addLog(user.toString(), super.getCurrentUserIp(), "添加用户", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        userMapper.insert(user);
    }

    /**
     * 修改用户
     */
    @Transactional
    public void update(User user) {
        User userInDb = this.findById(user.getId());
        StringBuilder sb = new StringBuilder();
        if (!userInDb.getPassword().equals(user.getPassword())) {
            sb.append("修改了密码；");
        }
        if (!userInDb.getNickName().equals(user.getNickName())) {
            sb.append("昵称由[").append(userInDb.getNickName()).append("]改为[").append(user.getNickName()).append("]；");
        }
        if (!userInDb.getContact().equals(user.getContact())) {
            sb.append("联系方式由[").append(userInDb.getContact()).append("]改为[").append(user.getContact()).append("]；");
        }
        if (user.getStatus() != null) {
            if (!userInDb.getStatus().equals(user.getStatus())) {
                sb.append("状态由[").append(userInDb.getStatus() == 1 ? "正常" : "禁用").append("]改为[").append(user.getStatus() == 1 ? "正常" : "禁用").append("]；");
            }
        }
        String content = "";
        if (!StringUtils.isEmpty(sb.toString())) {
            content = sb.toString().substring(0, sb.toString().length() - 1);
        }
        super.addLog(content, super.getCurrentUserIp(), "修改用户", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        userMapper.update(user);
    }

    /**
     * 通过主键查询
     */
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     * 通过用户名查询
     */
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    /**
     * 根据用户名查询一个用户的权限
     */
    public Set<String> findPermission(Integer userId) {
        return userPermissionService.findPermissionByUserName(userId);
    }

    /**
     * 分页查询用户
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @param keyword  查询关键字
     */
    public JSONArray findByPage(Integer pageNum, Integer pageSize, String keyword) {
        PageUtil pu = new PageUtil(pageNum, pageSize);
        List<User> list = userMapper.findByPage(pu, keyword);
        return JSON.parseObject(JSONArray.toJSONString(list), JSONArray.class);
    }

    /**
     * 统计用户总数量
     */
    public Long total(String keyword) {
        return userMapper.total(keyword);
    }

    /**
     * 删除用户
     */
    @Transactional
    public void delete(Integer id) {
        User userInDb = this.findById(id);
        super.addLog(userInDb.toString(), super.getCurrentUserIp(), "删除用户", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        userMapper.delete(id);
        // 删除该用户的所有权限
        userPermissionService.deleteUserPermission(id);
    }

}
