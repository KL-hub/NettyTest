package com.lixiang.netty.protocoltcp;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/22
 * @Version 1.0
 */
public class MessageProtocol {
    private int len; //关键
    private byte[] cotent;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getCotent() {
        return cotent;
    }

    public void setCotent(byte[] cotent) {
        this.cotent = cotent;
    }
}
