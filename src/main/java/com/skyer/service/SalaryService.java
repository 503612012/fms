package com.skyer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyer.vo.PaySalary;
import com.skyer.vo.Workinghour;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 工资服务层
 *
 * @author skyer
 */
@Service
public class SalaryService extends BaseService {

    @Resource
    private PaySalaryService paySalaryService;
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
        if ("byYear".equals(dateType)) {
            List<Map<String, Object>> list = workinghourService.countByYear(date, eid);
            for (Map<String, Object> item : list) {
                PaySalary paySalary = paySalaryService.findByEidAndWorkDate(((Integer) item.get("eid")), ((String) item.get("date")));
                if (paySalary == null) {
                    item.put("isPay", false);
                } else {
                    item.put("isPay", true);
                }
            }
            return JSON.parseObject(JSONArray.toJSONString(list), JSONArray.class);
        }
        List<Workinghour> list = workinghourService.findByEidAndDate(dateType, date, eid);
        return JSON.parseObject(JSONArray.toJSONString(list), JSONArray.class);
    }

    /**
     * 获取图表数据
     */
    public Object getChartsData(String dateType, String date, Integer eid) throws ParseException {
        if ("byMonth".equals(dateType)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(date));
            int dayNum = calendar.getActualMaximum(Calendar.DATE);
            JSONArray categories = new JSONArray();
            JSONArray salary = new JSONArray();
            for (int i = 1; i <= dayNum; i++) {
                String dateStr;
                if (i < 10) {
                    dateStr = date + "-0" + i;
                } else {
                    dateStr = date + "-" + i;
                }
                categories.add(dateStr);
                List<Workinghour> workinghours = workinghourService.findByEidAndDate("byDay", dateStr, eid);
                if (workinghours != null && workinghours.size() > 0) {
                    salary.add(workinghours.get(0).getDaySalary() * workinghours.get(0).getScore() / 10 + workinghours.get(0).getExtraSalary());
                } else {
                    salary.add(0);
                }
            }
            JSONObject result = new JSONObject();
            result.put("categories", categories);
            result.put("salary", salary);
            return result;
        } else {
            JSONArray categories = new JSONArray();
            JSONArray salary = new JSONArray();
            for (int i = 1; i <= 12; i++) {
                String dateStr;
                if (i < 10) {
                    dateStr = date + "-0" + i;
                } else {
                    dateStr = date + "-" + i;
                }
                categories.add(dateStr);
                Double monthSalary = workinghourService.sumMonthSalary(dateStr, eid);
                if (monthSalary != null) {
                    salary.add(monthSalary);
                } else {
                    salary.add(0);
                }
            }
            JSONObject result = new JSONObject();
            result.put("categories", categories);
            result.put("salary", salary);
            return result;
        }
    }

}
