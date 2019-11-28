package com.oven.enumerate;

/**
 * 统一返回枚举类
 *
 * @author Oven
 */
public enum ResultEnum {

    SUCCESS(200, "操作成功！"),
    SEARCH_ERROR(3000, "查询失败！"),
    INSERT_ERROR(3001, "添加失败！"),
    UPDATE_ERROR(3002, "修改失败！"),
    DELETE_ERROR(3003, "删除失败！"),
    NO_THIS_USER(3004, "该用户不存在！"),
    USER_DISABLE(3005, "账号已禁用！"),
    PASSWORD_WRONG(3006, "密码错误！"),
    CAPTCHA_TIMEOUT(3007, "验证码超时！"),
    CAPTCHA_WRONG(3008, "验证码错误！"),
    SESSION_TIMEOUT(3009, "未登录或会话超时，请重新登录！"),
    OTHER_LOGINED(3010, "会话失效，该账号已被其他人登录。请检查账号是否丢失或立即修改密码！"),
    USER_ALREADY_EXIST(3011, "该用户已经存在！"),
    HANDLE_ERROR(4444, "操作错误！");

    private int code;
    private String value;

    ResultEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
