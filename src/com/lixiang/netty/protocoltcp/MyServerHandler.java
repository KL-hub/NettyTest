package com.lixiang.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/22
 * @Version 1.0
 */

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol messageProtocol) throws Exception {
       //接收到数据，并处理
        int len = messageProtocol.getLen();
        byte[] cotent = messageProtocol.getCotent();
        System.out.println("服务器接收信息如下");
        System.out.println("长度 ="+len);
        System.out.println("内容"+new String(cotent,CharsetUtil.UTF_8));
        System.out.println("服务器接收到消息报数量"+(++count));

        //回复消息
        String responseContent = UUID.randomUUID().toString();
        int responseLen=responseContent.getBytes(CharsetUtil.UTF_8).length;
        byte[] bytes = responseContent.getBytes("UTF-8");
        //构建一个协议包
        MessageProtocol messageProtocol1 = new MessageProtocol();
        messageProtocol1.setLen(len);
        messageProtocol1.setCotent(bytes);
        ctx.writeAndFlush(messageProtocol1);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.close();
    }
}
