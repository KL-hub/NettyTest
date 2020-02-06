package com.lixiang.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Description //群聊客户端
 * @Author 李项
 * @Date 2020/2/5
 * @Version 1.0
 */
public class GroupChatClient {
    private final String HOST="127.0.0.1";
    private final int PORT=6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    //构造器，完成初始化工作
    public GroupChatClient() throws IOException {
        selector=Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        //将channel 注册到selectot
        socketChannel.register(selector, SelectionKey.OP_READ);
        userName=socketChannel.getLocalAddress().toString().substring(1);
    }
    //向服务器发送消息
    public void sendInfo(String info){
        info= userName+" 说 " +info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readInfo(){
        int count = 0;
        try {
            count = selector.select();
            if(count>0){        //有可以用的通过
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if(selectionKey.isReadable()){
                        //得到相关的通道
                        SocketChannel socketChannel=(SocketChannel)selectionKey.channel();
                        socketChannel.configureBlocking(false);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        socketChannel.read(byteBuffer);
                        System.out.println(new String(byteBuffer.array()).trim());
                    }
                }
                iterator.remove();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void main(String[] args) throws IOException {
        GroupChatClient groupChatClient = new GroupChatClient();
        new Thread(){
            @Override
            public void run(){
                groupChatClient.readInfo();
                try{
                    Thread.sleep(30000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
        //发送数据给服务端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s=scanner.nextLine();
            groupChatClient.sendInfo(s);
        }

    }
}
