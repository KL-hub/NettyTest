package com.lixiang.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description //webSocket长连接开发
 * @Author 李项
 * @Date 2020/2/18
 * @Version 1.0
 */
public class MyServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //handler()和childHandler()的主要区别是，handler()是发生在初始化的时候，childHandler()是发生在客户端连接之后
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //因为是基于http协议，使用http的编码与解码器
                            pipeline.addLast(new HttpServerCodec());
                            //是以块方式写，添加ChunkedWriteHandle处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            /**
                             *说明
                             * 1、http数据在传输过程中是分段，httpObjectAggregator就是可以将多个段聚合
                             * 2、这就是为什么，当浏览器发送大量数据时，就会发生多次http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 说明
                             * 1、对应webSocket,它的数据  是以 帧 形式传递
                             * 2、可以看到WebSocketFrame 下面有六个子类
                             * 3、浏览器请求时  ws://localhost:7000/hello 表示请求的url
                             * 4、WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议，保持长连接
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                            //自定义handle，处理业务逻辑
                            pipeline.addLast(new MyTestWebSocketFrameHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(6667).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    }
}
