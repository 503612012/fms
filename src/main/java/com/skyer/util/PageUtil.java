package com.skyer.util;

/**
 * 分页工具类
 *
 * @author skyer
 */
public class PageUtil {

    private Integer pageNum; // 当前页码
    private Integer pageSize; // 每页显示数量
    private Integer index; // 查询起始索引

    public PageUtil() {
        super();
    }

    public PageUtil(Integer pageNum, Integer pageSize) {
        this.pageSize = pageSize;
        this.index = (pageNum - 1) * pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

}
