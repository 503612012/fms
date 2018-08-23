package com.skyer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.skyer.vo.Workinghour;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工资服务层
 *
 * @author skyer
 */
@Service
public class SalaryService extends BaseService {

    @Resource
    private WorkinghourService workinghourService;

    /**
     * 统计工资
     *
     * @param dateType 日期类型
     * @param date     日期
     * @param eid      员工ID
     */
    public JSONArray countSalary(String dateType, String date, Integer eid) {
        List<Workinghour> list = workinghourService.findByEidAndDate(dateType, date, eid);
        return JSON.parseObject(JSONArray.toJSONString(list), JSONArray.class);
    }

}
