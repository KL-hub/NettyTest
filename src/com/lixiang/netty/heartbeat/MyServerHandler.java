package com.lixiang.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Description //心跳检测处理
 * @Author 李项
 * @Date 2020/2/18
 * @Version 1.0
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     *
     * @param ctx  上下文
     * @param evt  事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            //将evt 向下转型 IdleStatusEvent
            IdleStateEvent idleStateEvent= (IdleStateEvent) evt;
            String  eventType;
            switch (idleStateEvent.state()){
                case READER_IDLE:
                    eventType="读空闲";
                    break;
                case WRITER_IDLE:
                    eventType="写空闲";
                    break;
                case ALL_IDLE:
                    eventType="读写空闲";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + idleStateEvent.state());
            }
            System.out.println(ctx.channel().remoteAddress()+"--超时时间发生--"+eventType);
        }
    }
}
