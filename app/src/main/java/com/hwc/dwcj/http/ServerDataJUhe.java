package com.hwc.dwcj.http;

/**
 * 作   者：赵大帅
 * 描   述: 网络请求 基层数据封装
 * 日   期: 2017/11/13 17:50
 * 更新日期: 2017/11/13
 *
 * @author Administrator
 */
public class ServerDataJUhe {

    /**
     * reason : 成功返回
     * result : null
     * error_code : 0
     */

    private String reason;
    private Object result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
