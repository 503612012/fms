package com.skyer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.skyer.mapper.LogMapper;
import com.skyer.util.PageUtil;
import com.skyer.vo.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志服务层
 *
 * @author skyer
 */
@Service
public class LogService {

    @Resource
    private LogMapper logMapper;

    /**
     * 添加
     */
    public boolean insert(Log log) {
        return logMapper.insert(log) > 0;
    }

    /**
     * 分页查询日志
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @param keyword  查询关键字
     */
    public JSONArray findByPage(Integer pageNum, Integer pageSize, String keyword) {
        PageUtil pu = new PageUtil(pageNum, pageSize);
        List<Log> list = logMapper.findByPage(pu, keyword);
        return JSON.parseObject(JSONArray.toJSONString(list), JSONArray.class);
    }

    /**
     * 统计日志总数量
     */
    @Transactional
    public Long total(String keyword) {
        return logMapper.total(keyword);
    }

}
