package com.brs.ailblog.vo;

/**
 * @Description: 返回对象
 * @Author: DC
 * @Date: created in 11:31 2018/6/6
 */
public class Response {

    private  boolean success;//处理是否成功
    private String message;//处理后消息提升
    private Object body;//返回数据

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response(boolean success, String message, Object body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
