package com.hwc.dwcj.http;

import java.io.Serializable;

/**
 * 作   者：花伟成
 * 描   述: 网络请求 基层数据封装
 * 日   期: 2021/4/28 17:50
 *
 * @author Administrator
 */
public class ServerData implements Serializable {
    private static final long serialVersionUID = 1L;


    private int code;
    private String status;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
