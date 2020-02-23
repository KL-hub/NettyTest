package com.lixiang.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/22
 * @Version 1.0
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MyMessageDecoder()); //加入解码器
        pipeline.addLast(new MyMessageEncode());
        pipeline.addLast(new MyServerHandler());
    }
}
