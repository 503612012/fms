package com.skyer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.skyer.mapper.PaySalaryMapper;
import com.skyer.util.PageUtil;
import com.skyer.vo.PaySalary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 工资发放记录服务层
 *
 * @author skyer
 */
@Service
public class PaySalaryService extends BaseService {

    @Resource
    private PaySalaryMapper paySalaryMapper;

    /**
     * 添加工资发放记录
     */
    @Transactional
    public void insert(PaySalary paySalary) {
        super.addLog(paySalary.toString(), super.getCurrentUserIp(), "添加工资发放记录", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        paySalaryMapper.insert(paySalary);
    }

    /**
     * 分页查询工资发放记录
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     */
    public JSONArray findByPage(Integer pageNum, Integer pageSize, String workDate, String payDate, Integer eid) {
        PageUtil pu = new PageUtil(pageNum, pageSize);
        List<Map<String, Object>> list = paySalaryMapper.findByPage(pu, workDate, payDate, eid);
        return JSON.parseObject(JSONArray.toJSONString(list), JSONArray.class);
    }

    /**
     * 统计工资发放记录总数量
     */
    public Long total(String workDate, String payDate, Integer eid) {
        return paySalaryMapper.total(workDate, payDate, eid);
    }

    /**
     * 条件查询
     */
    public PaySalary findByEidAndWorkDate(Integer eid, String date) {
        return paySalaryMapper.findByEidAndWorkDate(eid, date);
    }

}
