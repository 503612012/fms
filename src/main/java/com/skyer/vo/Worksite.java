package com.skyer.vo;

/**
 * 工地实体类
 *
 * @author skyer
 */
public class Worksite {

    private Integer id; // 工地
    private String name; // 名称
    private String desc; // 描述
    private Integer createId; // 创建人ID
    private String createNickName; // 创建人昵称
    private String createDate; // 创建时间
    private String lastModifyDate; // 最后修改时间
    private Integer lastModifyId; // 最后修改人ID

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getCreateNickName() {
        return createNickName;
    }

    public void setCreateNickName(String createNickName) {
        this.createNickName = createNickName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Integer getLastModifyId() {
        return lastModifyId;
    }

    public void setLastModifyId(Integer lastModifyId) {
        this.lastModifyId = lastModifyId;
    }

    @Override
    public String toString() {
        return "Worksite{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", createId=" + createId +
                ", createNickName='" + createNickName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", lastModifyDate='" + lastModifyDate + '\'' +
                ", lastModifyId='" + lastModifyId + '\'' +
                '}';
    }

}
