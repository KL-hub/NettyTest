package com.lixiang.netty.websocket;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @Description //webSocket处理帧
 * @Author 李项
 * @Date 2020/2/18
 * @Version 1.0
 */
//TextWebSocketFrame类型，表示一个文本帧
public class MyTestWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器收到消息 "+msg.text());

        //回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间"+ LocalDateTime.now())+" "+msg.text());
    }

     //当web客户端连接后，触发方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id表示一个值，LongText是唯一的
        System.out.println("handlerAdded 被调用"+ctx.channel().id().asLongText());
        System.out.println("handlerAdded 被调用"+ctx.channel().id().asShortText());
    }
    //客户端断开后，调用
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用"+ctx.channel().id().asLongText());
        System.out.println("handlerRemoved被调用"+ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生，关闭通道");
    }
}
