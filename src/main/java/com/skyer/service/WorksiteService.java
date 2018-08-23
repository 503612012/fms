package com.skyer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyer.mapper.WorksiteMapper;
import com.skyer.util.PageUtil;
import com.skyer.vo.Worksite;
import org.springframework.stereotype.Service;

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
        worksiteMapper.update(worksite);
    }

    /**
     * 删除
     */
    public int delete(Integer id) {
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
