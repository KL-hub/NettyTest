package com.lixiang.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/3
 * @Version 1.0
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("a.txt");
        FileChannel channel = fileInputStream.getChannel(); 
        FileOutputStream fileOutputStream = new FileOutputStream("c.txt");
        FileChannel channel1 = fileOutputStream.getChannel();
        channel1.transferFrom(channel,0,channel.size());
        //关闭相关的通道和流
        channel.close();
        channel1.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
