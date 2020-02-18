package com.lixiang.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Description //自定义handle
 * @Author 李项
 * @Date 2020/2/6
 * @Version 1.0
 */
public class NettyServerHandle extends ChannelInboundHandlerAdapter {
   //读取数据显示

    /**
     *
     * @param ctx 上下文对象，含有管道pipeline,通道Channel,地址
     * @param msg 就是客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取客户端发送的StudentPojo.Student
        StudentPOJO.Student student=(StudentPOJO.Student)msg;
        System.out.println("客户端发送的数据"+student.getId()+" name= "+student.getName());

    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存，并刷新
        //一般讲，我们对数据进行编码

        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端",CharsetUtil.UTF_8));
    }
    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
