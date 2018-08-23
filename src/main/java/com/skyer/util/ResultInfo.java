package com.skyer.util;

/**
 * 自定义返回类
 *
 * @author skyer
 */
public class ResultInfo<T> {

    private Integer code; // 返回代码(200:成功、其他:失败)
    private T data; // 返回的数据，正确的信息或错误描述信息

    public ResultInfo() {
        super();
    }

    public ResultInfo(Integer code, T data) {
        super();
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public ResultInfo<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultInfo<T> setData(T data) {
        this.data = data;
        return this;
    }

}
