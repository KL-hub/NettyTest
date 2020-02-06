package com.lixiang.nio.groupchat;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Description //群聊服务端
 * @Author 李项
 * @Date 2020/2/5
 * @Version 1.0
 */
public class GroupChatServer {
    //定义属性
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final  int PORT=6667;
    //构造器、初始化
    public GroupChatServer(){
        try {
             selector = Selector.open();
             serverSocketChannel=ServerSocketChannel.open();
             //绑定端口
             serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
             //设置为非阻塞
             serverSocketChannel.configureBlocking(false);
             //将该channeL，注册到selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void  listen(){
        try{
            while (true){
                int count = selector.select(2000);
                if(count>0){ //如果有事件处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        if(selectionKey.isAcceptable()){
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector,SelectionKey.OP_READ);
                            //输出提示
                            System.out.println(accept.getRemoteAddress()+"上线");
                        }
                        if (selectionKey.isReadable()){//通道发送read事件
                            //
                        }
                        //把当前的selectKey删除，防止重复操作
                        iterator.remove();
                    }

                }else {
                    System.out.println("等待");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void readData(SelectionKey selectionKey){
        //定义一个socketChannel
        SocketChannel socketChannel=null;
        try{
            socketChannel=(SocketChannel)selectionKey.channel();
            //创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(byteBuffer);
            if(count>0){
                String s = new String(byteBuffer.array());
                System.out.println("form client"+ s);
                //向其他客户端转发消息
                sendInfoToOtherClient(s,socketChannel);
            }

        }catch (IOException e){
            try {
                System.out.println(socketChannel.getRemoteAddress()+"下线了");
                //取消注册
                selectionKey.cancel();;
                //通道关闭
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    //向其他客户端转发消息
    private void sendInfoToOtherClient(String msg,SocketChannel self) throws IOException {
        for (SelectionKey key : selector.keys()) {
            SelectableChannel targetChannle = key.channel();
            if(targetChannle!= self){
                SocketChannel socketChannel=(SocketChannel)targetChannle;
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer数据写入到通道
                socketChannel.write(byteBuffer);
            }
        }
        
    }
    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
