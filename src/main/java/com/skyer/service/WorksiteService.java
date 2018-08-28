package com.skyer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyer.mapper.WorksiteMapper;
import com.skyer.util.PageUtil;
import com.skyer.vo.Worksite;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工地服务层
 *
 * @author skyer
 */
@Service
public class WorksiteService extends BaseService {

    @Resource
    private WorksiteMapper worksiteMapper;

    /**
     * 分页查询工地
     *
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @param keyword  查询关键字
     */
    public JSONArray findByPage(Integer pageNum, Integer pageSize, String keyword) {
        PageUtil pu = new PageUtil(pageNum, pageSize);
        List<Worksite> list = worksiteMapper.findByPage(pu, keyword);
        return JSON.parseObject(JSONArray.toJSONString(list), JSONArray.class);
    }

    /**
     * 统计工地总数量
     */
    public Long total(String keyword) {
        return worksiteMapper.total(keyword);
    }

    /**
     * 添加
     */
    public int insert(Worksite worksite) {
        super.addLog(worksite.toString(), super.getCurrentUserIp(), "添加工地", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        return worksiteMapper.insert(worksite);
    }

    /**
     * 通过主键查询
     */
    public Worksite findById(Integer id) {
        return worksiteMapper.findById(id);
    }

    /**
     * 更新
     */
    public void update(Worksite worksite) {
        Worksite worksiteInDb = worksiteMapper.findById(worksite.getId());
        StringBuilder sb = new StringBuilder();
        if (!worksite.getName().equals(worksiteInDb.getName())) {
            sb.append("工地名称由[").append(worksiteInDb.getName()).append("]改为[").append(worksite.getName()).append("]；");
        }
        if (!worksite.getDesc().equals(worksiteInDb.getDesc())) {
            sb.append("工地描述由[").append(worksiteInDb.getDesc()).append("]改为[").append(worksite.getDesc()).append("]；");
        }
        String content = "";
        if (!StringUtils.isEmpty(sb.toString())) {
            content = sb.toString().substring(0, sb.toString().length() - 1);
        }
        super.addLog(content, super.getCurrentUserIp(), "修改工地", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        worksiteMapper.update(worksite);
    }

    /**
     * 删除
     */
    public int delete(Integer id) {
        Worksite worksite = worksiteMapper.findById(id);
        super.addLog(worksite.toString(), super.getCurrentUserIp(), "删除工地", super.getCurrentUser().getId(), super.getCurrentUser().getNickName());
        return worksiteMapper.delete(id);
    }

    /**
     * 查询所有工地
     */
    public JSONArray findAll() {
        List<Worksite> list = worksiteMapper.findAll();
        JSONArray result = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("id", list.get(i).getId());
            obj.put("text", list.get(i).getName());
            if (i == 0) {
                obj.put("selected", true);
            }
            result.add(obj);
        }
        return result;
    }

}
