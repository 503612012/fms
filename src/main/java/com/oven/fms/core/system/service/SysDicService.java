package com.oven.fms.core.system.service;

import com.oven.fms.common.redis.IRedisService;
import com.oven.fms.core.system.dao.SysDicDao;
import com.oven.fms.core.system.entity.SysDicEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 系统级字典Service层
 *
 * @author Oven
 */
@Slf4j
@Service
public class SysDicService {

    @Resource
    private SysDicDao sysDicDao;
    @Resource
    private IRedisService redisService;

    /**
     * 模拟秒杀接口
     */
    public void secKill() {
        // 开始多线程去秒杀
        for (int i = 0; i < 50; i++) {
            int thread_index = i;
            new Thread(() -> {
                for (int n = 0; n < 30; n++) {
                    doSecKill(thread_index, n);
                }
            }).start();
        }
    }

    /**
     * 开始秒杀
     */
    private void doSecKill(int thread_index, int n) {
        // 获取分布式锁
        String lockId = redisService.acquireLock("SEC_KILL_LOCK", TimeUnit.MINUTES.toSeconds(30));
        try {
            while (StringUtils.isEmpty(lockId)) {
                TimeUnit.MILLISECONDS.sleep(100); // 等待100毫秒后重新获取分布式锁
                lockId = redisService.acquireLock("SEC_KILL_LOCK", TimeUnit.MINUTES.toSeconds(30));
            }
            reduceNum(thread_index, n); // 生成订单
        } catch (Exception e) {
            log.error("秒杀活动异常：", e);
        } finally {
            // 释放分布式锁
            redisService.releaseLock("SEC_KILL_LOCK", lockId);
        }
    }

    /**
     * 生成订单
     */
    private void reduceNum(int thread_index, int n) {
        try {
            // 获取库存剩余量
            int num = Integer.parseInt(sysDicDao.getByKey("secKill").getValue());
            if (num > 0) { // 库存还有
                log.info("=========================== >>> 当前库存：{}，线程【{} - {}】售出一件！", num, thread_index, n);
                sysDicDao.reduceNum();
            } else {
                log.info("=========================== >>> 当前库存：{}，线程【{} - {}】库存不足！", num, thread_index, n);
            }
        } catch (Exception e) {
            log.error("生成订单异常：", e);
        }
    }

    /**
     * 查询所有
     */
    public List<SysDicEntity> getAll() {
        return sysDicDao.getAll();
    }

    /**
     * 添加数据字典
     */
    public void save(SysDicEntity sysDic) throws Exception {
        sysDicDao.save(sysDic);
    }

    /**
     * 更新
     */
    public void update(SysDicEntity sysDic) {
        sysDicDao.update(sysDic);
    }

    /**
     * 通过主键查询
     */
    public SysDicEntity getById(Integer id) {
        return sysDicDao.getById(id);
    }

    /**
     * 分页查询数据字典
     */
    public List<SysDicEntity> getByPage(SysDicEntity sysDic) {
        return sysDicDao.getByPage(sysDic);
    }

    /**
     * 获取数据字典总数量
     */
    public Integer getTotalNum(SysDicEntity sysDic) {
        return sysDicDao.getTotalNum(sysDic);
    }

    /**
     * 删除数据字典
     */
    public boolean delete(Integer id) throws Exception {
        return sysDicDao.delete(id) > 0;
    }

    /**
     * 修改状态
     */
    public void updateStatus(Integer id, Integer status) {
        sysDicDao.updateStatus(id, status);
    }

    /**
     * 根据key查询
     */
    public SysDicEntity getByKey(String key) {
        return sysDicDao.getByKey(key);
    }

}
