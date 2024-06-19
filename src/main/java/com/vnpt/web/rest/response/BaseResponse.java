package com.vnpt.web.rest.response;

public class BaseResponse<T> {
    private String code;
    private String desc;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void success(T data){
        this.code = "200";
        this.desc = "Success";
        this.data = data;
    }
}
