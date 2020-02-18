package com.lixiang.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/7
 * @Version 1.0
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    //当通道就绪就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ctx"+ctx);

        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,server", CharsetUtil.UTF_8));
    }
    //当通道有读取事件时，触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        ByteBuf byteBuf=(ByteBuf)msg;
        System.out.println("服务器回复的消息："+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址"+ctx.channel().remoteAddress());
    }
    //异常发生时，触发
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
