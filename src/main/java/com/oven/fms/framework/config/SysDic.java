package com.oven.fms.framework.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentMap;

/**
 * 系统级字典类
 *
 * @author Oven
 */
@Slf4j
public class SysDic {

    private static ConcurrentMap<String, Object> sysDic;

    static void setSysDic(ConcurrentMap<String, Object> sysDic) {
        SysDic.sysDic = sysDic;
    }

    /**
     * 从字典中查找key-value数据 如果查找则返回value查询不到则返回null
     */
    public static String getProperty(String key) {
        if (sysDic == null) {
            log.error("系统字典数据加载失败");
        }
        return (String) sysDic.get(key);
    }

}
