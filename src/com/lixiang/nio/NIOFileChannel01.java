package com.lixiang.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/3
 * @Version 1.0
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws IOException {
        String s="helo,world";
        //创建一个输出流->channel
        FileOutputStream fileOutputStream = new FileOutputStream("a.txt");
        //通过fileOutpoutStream获取对应的fileChannel
        FileChannel channel = fileOutputStream.getChannel();
        //创建一个缓冲区bytebuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将str放入到byteBuffer
        byteBuffer.put(s.getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
        fileOutputStream.close();
    }
}
