package com.lixiang.netty.inboundandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/19
 * @Version 1.0
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //编码
        pipeline.addLast(new MyLongByteEncoder());
        //处理逻辑
        pipeline.addLast(new MyClientHandler());

    }
}
