package com.skyer.controller.worksite;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skyer.controller.base.BaseController;
import com.skyer.enumerate.ResultEnum;
import com.skyer.service.WorksiteService;
import com.skyer.vo.Worksite;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 工地控制器
 *
 * @author skyer
 */
@Controller
@RequestMapping("/worksite/worksite")
public class WorksiteController extends BaseController {

    private final static Logger L = Logger.getLogger(WorksiteController.class);

    @Resource
    private WorksiteService worksiteService;

    /**
     * 去到工地管理主页面
     */
    @RequestMapping("/index")
    @RequiresPermissions("D1_01")
    public ModelAndView index() {
        try {
            return new ModelAndView("/view/worksite/index");
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询工地列表
     *
     * @param page    当前页码
     * @param rows    每页显示数量
     * @param keyword 搜索关键字
     */
    @RequestMapping("/list")
    @RequiresPermissions("D1_01")
    @ResponseBody
    public Object list(Integer page, Integer rows, String keyword, HttpServletRequest req) {
        try {
            JSONArray list = worksiteService.findByPage(page, rows, keyword);
            JSONObject result = new JSONObject();
            Long total = worksiteService.total(keyword);
            result.put("rows", list);
            result.put("total", total);
            req.setAttribute("keyword", StringUtils.isEmpty(keyword) ? "" : keyword);
            return result;
        } catch (Exception e) {
            L.error("---------------------------入参[page:" + page + ", rows:" + rows + ", keyword:" + keyword + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

    /**
     * 添加工地
     */
    @RequestMapping("/add")
    @ResponseBody
    @RequiresPermissions("D1_01_01")
    public Object add(Worksite worksite) {
        try {
            worksite.setCreateDate(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            worksite.setCreateId(super.getCurrentUser().getId());
            worksite.setCreateNickName(super.getCurrentUser().getNickName());
            worksite.setLastModifyDate(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            worksite.setLastModifyId(super.getCurrentUser().getId());
            worksiteService.insert(worksite);
            return super.success("添加成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[" + worksite.toString() + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.INSERT_ERROR.getCode(), ResultEnum.INSERT_ERROR.getValue());
    }

    /**
     * 修改工地
     */
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("D1_01_02")
    public Object update(Worksite worksite) {
        try {
            Worksite worksiteInDb = worksiteService.findById(worksite.getId());
            worksiteInDb.setLastModifyDate(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
            worksiteInDb.setLastModifyId(super.getCurrentUser().getId());
            worksiteInDb.setDesc(worksite.getDesc());
            worksiteInDb.setName(worksite.getName());
            worksiteService.update(worksiteInDb);
            return super.success("修改成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[" + worksite.toString() + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.UPDATE_ERROR.getCode(), ResultEnum.UPDATE_ERROR.getValue());
    }

    /**
     * 删除工地
     */
    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions("D1_01_03")
    public Object delete(Integer id) {
        try {
            worksiteService.delete(id);
            return super.success("删除成功！");
        } catch (Exception e) {
            L.error("---------------------------入参[id:" + id + "]", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.DELETE_ERROR.getCode(), ResultEnum.DELETE_ERROR.getValue());
    }

    /**
     * 查询所有工地
     */
    @RequestMapping("/findAll")
    @ResponseBody
    @RequiresPermissions("C2_02")
    public Object findAll() {
        try {
            return worksiteService.findAll();
        } catch (Exception e) {
            L.error("---------------------------", e);
            e.printStackTrace();
        }
        return super.fail(ResultEnum.SEARCH_ERROR.getCode(), ResultEnum.SEARCH_ERROR.getValue());
    }

}
