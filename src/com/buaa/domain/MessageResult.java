package com.buaa.domain;

public class MessageResult {
    private boolean right;
    private int messageType;
    private String jsonString;

    public MessageResult() {
        super();
    }

    public MessageResult(boolean right) {
        super();
        this.right = right;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

}
