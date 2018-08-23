package com.skyer.vo;

/**
 * 用户实体类
 *
 * @author skyer
 */
public class User {

    private Integer id; // 主键
    private String userName; // 用户名
    private String password; // 密码
    private String nickName; // 昵称
    private Integer status; // 状态(1正常,2禁用)
    private String contact; // 联系方式
    private String createTime; // 创建时间
    private String modifyTime; // 最后修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", nickName=" + nickName + ", status=" + status + ", contact=" + contact + ", createTime=" + createTime + ", modifyTime=" + modifyTime + "]";
    }

}
