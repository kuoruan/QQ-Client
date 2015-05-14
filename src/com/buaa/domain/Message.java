package com.buaa.domain;

public class Message {
    private int pid;// 消息pid
    private String content;// 消息内容
    private int fromPid;// 发送者的pid
    private int toPid;// 发给那个人的pid
    private long sendTime;// 发送的时间
    private int isRead;// 是否阅读

    public Message(String content, int fromPid, int toPid, long sendTime) {
        super();
        this.content = content;
        this.fromPid = fromPid;
        this.toPid = toPid;
        this.sendTime = sendTime;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFromPid() {
        return fromPid;
    }

    public void setFromPid(int fromPid) {
        this.fromPid = fromPid;
    }

    public int getToPid() {
        return toPid;
    }

    public void setToPid(int toPid) {
        this.toPid = toPid;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

}
