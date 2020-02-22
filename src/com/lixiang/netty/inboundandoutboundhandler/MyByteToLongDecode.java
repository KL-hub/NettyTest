package com.lixiang.netty.inboundandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/19
 * @Version 1.0
 */
public class MyByteToLongDecode extends ByteToMessageDecoder {
    /**
     * decoode 会根据接收的数据，被多次调用，直到确定没有新的元素添加到list
     *      或者是Byte是ByteBuf时，没有更多的刻度字节位置
     *
     * 如果list out不为空，就会将list的内容传递给下一个channelinboundhandler处理，
     * 该处理器的方法也会被调用多次
     * @param ctx 上下文对象
     * @param in  入站的ByteBuf
     * @param out List集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //long8个字节
        if (in.readByte() >=8){
            out.add(in.readLong());
        }
    }
}
