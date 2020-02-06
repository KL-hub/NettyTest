package com.lixiang.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description //NIO服务
 * @Author 李项
 * @Date 2020/2/5
 * @Version 1.0
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建ServerSockerChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建一个Selector对象
        Selector selector = Selector.open();
        //绑定一个端口6666
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSockerChannel注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while(true){
            if (selector.select(1000)==0){ //没有事件发生
                System.out.println("服务器等待了1s,无连接");
                continue;
            }
            //如果返回的大于0,就获取相关的selectKey集合
            //1、如果返回的大于0，表示已经获取到关注的事件
            //2、select.selectKeys,返回关注事件的集合
            //3、通过selectKey反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //根据key对应通道发生的事件做相应处理
                if (selectionKey.isAcceptable()){//如果是OP_ACCECPT，有新的客户端连接
                    //该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //将SocketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //注册并关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }else if(selectionKey.isReadable()){//发生OP_READ
                    //根据selectKey获取对应Channel
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    //获取该channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("客户端发送数据--"+new String(byteBuffer.array()));
                }

                //手动从集合中移除当前的selectkey，防止重复操作。
                selectionKeys.remove(selectionKey);
          }

        }
    }
}
