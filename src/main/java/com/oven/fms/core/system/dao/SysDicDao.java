package com.oven.fms.core.system.dao;

import com.oven.basic.base.dao.BaseDao;
import com.oven.basic.base.entity.ConditionAndParams;
import com.oven.basic.base.entity.UpdateColumn;
import com.oven.fms.core.system.entity.SysDicEntity;
import org.springframework.stereotype.Repository;

/**
 * 系统级字典dao层
 *
 * @author Oven
 */
@Repository
public class SysDicDao extends BaseDao<SysDicEntity> {

    /**
     * 更新
     */
    public int update(SysDicEntity sysdic) {
        return super.update(UpdateColumn.update("_value", sysdic.getValue()).and("_desc", sysdic.getDesc()), ConditionAndParams.build("dbid", sysdic.getId()));
    }

    /**
     * 修改状态
     */
    public void updateStatus(Integer id, Integer status) {
        super.update(UpdateColumn.update("_status", status), ConditionAndParams.build("dbid", id));
    }

    /**
     * 根据key查询
     */
    public SysDicEntity getByKey(String key) {
        return super.getOne(ConditionAndParams.build("_key", key));
    }

    public void reduceNum() {
        super.decrement("_value", ConditionAndParams.build("_key", "secKill"));
    }

}
