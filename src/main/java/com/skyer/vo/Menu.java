package com.skyer.vo;

/**
 * 目录实体类
 *
 * @author skyer
 */
public class Menu {

    private Integer id; // 目录表
    private String menuCode; // 目录编码
    private String menuName; // 目录名称
    private Integer pid; // 父Id
    private Integer sort; // 排序值
    private String url; // 目录链接
    private String iconCls; // 图标
    private String type; // 1目录,2按钮
    private String createTime; // 创建时间
    private String modifyTime; // 最后修改时间
    private Integer status; // 状态(1正常,2禁用)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", menuCode=" + menuCode + ", menuName=" + menuName + ", pid=" + pid + ", sort=" + sort + ", url=" + url + ", iconCls=" + iconCls + ", type=" + type + ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", status=" + status + "]";
    }

}
