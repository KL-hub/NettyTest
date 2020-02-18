package com.lixiang.netty;
import	java.nio.channels.Channel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/6
 * @Version 1.0
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //1、创建BossGroup和workerGroup
        //2、bossGroup只是处理连接请求，真正的和客户端业务处理，会叫给workerGroup完成
        //3、两个都是无限循环
        //4、bossGroup和workerGroup含有的子线程的个数
        //   默认cpu核数*2

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //使用链式编程来进行配置
            serverBootstrap.group(bossGroup, workerGroup)        //设置两个线程组
                    .channel(NioServerSocketChannel.class)      //使用NioSocketChannel作为服务器的通道
                    .option(ChannelOption.SO_BACKLOG, 128)   //设置线程对列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)   //设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { //创建一个通道测试对象
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandle());
                        }
                    });
            System.out.println(" 服务器 is ready ...");

            ChannelFuture cf = serverBootstrap.bind(6668).sync();
            //给cf进行监听，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(cf.isSuccess()){
                        System.out.println("监听端口 6668 成功");
                    }else {
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
