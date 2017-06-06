package com.sugood.app.entity;

/**
 * Package :com.sugood.app.entity
 * Description :
 * Author :Rc3
 * Created at :2017/04/14 16:13.
 */

public class AddressList {


    /**
     * message : null
     * success : true
     * content : {}
     */

    private Object message;
    private boolean success;
    private ContentBean content;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
    }
}
