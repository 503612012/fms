package com.oven.fms.core.user.dao;

import com.oven.basic.base.dao.BaseDao;
import com.oven.basic.base.entity.Condition;
import com.oven.basic.base.entity.ConditionAndParams;
import com.oven.basic.base.entity.UpdateColumn;
import com.oven.fms.core.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户dao层
 *
 * @author Oven
 */
@Repository
public class UserDao extends BaseDao<User> {

    /**
     * 通过用户名查询
     *
     * @param userName 用户名
     */
    public User getByUserName(String userName) {
        return super.getOne(ConditionAndParams.build(User::getUserName, userName));
    }

    public void updateLastLoginTime(String time, Integer userId) {
        super.update(UpdateColumn.update("last_login_time", time).and("err_num", 0), ConditionAndParams.build("dbid", userId));
    }

    /**
     * 登录密码错误次数加一
     */
    public void logPasswordWrong(Integer userId) {
        super.increment("err_num", ConditionAndParams.build("dbid", userId));
    }

    /**
     * 更新头像
     */
    public void updateAvatar(Integer id, String avatarFileName) {
        super.update(UpdateColumn.update("avatar", avatarFileName), ConditionAndParams.build("dbid", id));
    }

    /**
     * 重置错误次数
     */
    public void resetErrNum(Integer userId) {
        super.update(UpdateColumn.update("err_num", 0), ConditionAndParams.build("dbid", userId));
    }

    /**
     * 修改用户个性化配置
     */
    public void updateConfig(Integer id, String config) {
        super.update(UpdateColumn.update("config", config), ConditionAndParams.build("dbid", id));
    }

    public List<User> getByIds(List<Integer> ids) {
        return super.getAll(ConditionAndParams.build("dbid", Condition.in, ids));
    }

}
