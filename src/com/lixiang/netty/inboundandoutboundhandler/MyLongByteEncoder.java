package com.lixiang.netty.inboundandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/19
 * @Version 1.0
 */
public class MyLongByteEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongByteEncoder  encoder被调用");
        System.out.println("msg="+msg);
        out.writeLong(msg);
    }
}
