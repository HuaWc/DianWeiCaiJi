package com.zds.base.upDated.model;

/**
 * @author Administrator
 *         日期 2018/5/8
 *         描述
 */

public class ServerDateInfo {

    /**
     * success : true
     * resultCode : 1000
     * message : [1000] 安卓版本检查成功
     * data : null
     */

    private boolean success;
    private int resultCode;
    private String message;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
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
