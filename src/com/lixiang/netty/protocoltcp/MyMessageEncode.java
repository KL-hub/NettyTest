package com.lixiang.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/23
 * @Version 1.0
 */
public class MyMessageEncode extends MessageToByteEncoder<MessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyMessageEncode  encode 方法被调用");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getCotent());
    }
}
