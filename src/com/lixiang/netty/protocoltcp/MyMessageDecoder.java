package com.lixiang.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/23
 * @Version 1.0
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder decoder 被调用");
        //需要将的带二进制字节码->MessageProtocol 数据包(对象)
        int len = in.readInt();
        byte[] content = new byte[len];
        ByteBuf byteBuf = in.readBytes(content);
        //封装成 MessageProtocol对象，放入out，传递下一个handler业务处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setCotent(content);

        out.add(messageProtocol);


    }
}
