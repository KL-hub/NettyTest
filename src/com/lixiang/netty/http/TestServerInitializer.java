package com.lixiang.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/7
 * @Version 1.0
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器
        
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个netty 提供的httpServerCodec
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());

    }
}
