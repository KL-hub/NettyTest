package com.lixiang.netty;

import io.netty.buffer.ByteBuf;
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
        /*System.out.println(" server ctx"+ctx);
        //将msg转成一个ByteBuffer
        ByteBuf byteBuf=(ByteBuf)msg;
        System.out.println("客户端发送的消息"+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址"+ctx.channel().remoteAddress());*/

        //比如这里我们有一个非常耗时的业务->异步执行->提交该channel对应得NIOEventLoop的taskQueue中
        //解决方案1
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10*1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,睡眠10s的客户端",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("发生异常"+e.getMessage());
                }
            }
        });
        //用户自定义定时任务-》该任务是提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10*1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,睡眠10s的客户端",CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("发生异常"+e.getMessage());
                }
            }
        },5, TimeUnit.SECONDS);

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
