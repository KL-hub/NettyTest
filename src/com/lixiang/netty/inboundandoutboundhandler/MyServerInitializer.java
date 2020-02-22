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
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //入站的handler进行解码
        pipeline.addLast(new MyByteToLongDecode());
        //自定义的handler 处理业务逻辑
        pipeline.addLast(new MyServerHandler());
    }
}
