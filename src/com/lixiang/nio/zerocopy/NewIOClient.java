package com.lixiang.nio.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/6
 * @Version 1.0
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("1270.0.1",7001));
        String fileName="a.txt";
        FileChannel channel = new FileInputStream(new File(fileName)).getChannel();
        //准备发送
        long startTime=System.currentTimeMillis();
        //Linux下一个transferTo方法就可以完成传输
        //在windows下一次调用transferTo只能发送 8m,就需要分段传输文件，而且要主要出书时的位置
        //transerTo底层使用了零拷贝
        long transerCount = channel.transferTo(0, channel.size(), socketChannel);
        System.out.println("发送的总字节数="+transerCount+"耗时"+(System.currentTimeMillis() - startTime));

        //关闭
        channel.close();
        socketChannel.close();


    }
}
