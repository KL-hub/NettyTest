package com.lixiang.nio;
import	java.nio.ByteBuffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Description //scatting将数据写入到buffer时，可以采用buffer数组，依次写入
 *              //gathing:从buffer读取数据时，可以采用buffer数组，依次读
 * @Author 李项
 * @Date 2020/2/3
 * @Version 1.0
 */
public class ScattingAndGatheringTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定端口到socket,并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);
        byteBuffers[1]=ByteBuffer.allocate(3);
        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength=8;
        while(true){
            int byteRead=0;
            while(byteRead< messageLength){
                long l =socketChannel.read(byteBuffers);
                byteRead+=l; //累计读取的字节数
                System.out.println("byteRead"+byteRead);
                Arrays.asList(byteBuffers).stream().forEach(System.out::println);
            }
            //将所有的buffer进行flip
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
            //将数据显示到客户端
            long byteWrite=0;
            while (byteWrite<messageLength){
                long l=socketChannel.write(byteBuffers);
                byteWrite+=l;
            }
            //将所有的buffer进行clear
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
            System.out.println("byteRead="+byteRead+"byteWrite="+byteWrite+"messageLength"+messageLength);
        }

    }

}
