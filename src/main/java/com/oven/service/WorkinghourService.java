package com.oven.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.oven.mapper.WorkinghourMapper;
import com.oven.util.PageUtil;
import com.oven.vo.Workinghour;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 工时服务层
 *
 * @author Oven
 */
@Service
public class WorkinghourService extends BaseService {

    @Resource
    private WorkinghourMapper workinghourMapper;

    /**
     * 分页查询工时
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @param date     日期
     * @param keyword  查询关键字
     */
    public JSONArray findByPage(Integer pageNum, Integer pageSize, String date, String keyword) {
        PageUtil pu = new PageUtil(pageNum, pageSize);
        List<Workinghour> list = workinghourMapper.findByPage(pu, date, keyword);
        return JSON.parseObject(JSONArray.toJSONString(list), JSONArray.class);
    }

    /**
     * 统计工时总数量
     */
    public Long total(String date, String keyword) {
        return workinghourMapper.total(date, keyword);
    }

    /**
     * 添加
     */
    public boolean insert(Workinghour workinghour) {
        super.addLog(workinghour.toString(), super.getCurrentUserIp(), "添加工时", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        return workinghourMapper.insert(workinghour) > 0;
    }

    /**
     * 查询是否已经录入
     */
    public boolean isInput(Integer eid, String date) {
        return workinghourMapper.isInput(eid, date) > 0;
    }

    /**
     * 根据员工ID和日期删除
     */
    public void deleteByEidAndDate(Integer eid, String date) {
        workinghourMapper.deleteByEidAndDate(eid, date);
    }

    /**
     * 通过员工ID和日期查询
     */
    public List<Workinghour> findByEidAndDate(String dateType, String date, Integer eid) {
        return workinghourMapper.findByEidAndDate(dateType, date, eid);
    }

    /**
     * 统计某员工某月总薪资
     */
    public Double sumMonthSalary(String date, Integer eid) {
        return workinghourMapper.sumMonthSalary(date, eid);
    }

    /**
     * 统计某员工年总薪资
     */
    public List<Map<String, Object>> countByYear(String date, Integer eid) {
        return workinghourMapper.countByYear(date, eid);
    }

}
