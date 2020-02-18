package com.lixiang.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/7
 * @Version 1.0
 */
// SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
//HttpObject是客户端与服务器相互通讯的数据被封装成HttpObject
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest){
            System.out.println("msg类型="+msg.getClass());
            System.out.println("客户端地址"+ctx.channel().remoteAddress());

            HttpRequest request=(HttpRequest)msg;

            //回复信息给服务器
            ByteBuf content= Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);
            //构造一个http的响应，即httpresponse
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,"text/html");
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            ctx.writeAndFlush(fullHttpResponse);
        }
    }
}
