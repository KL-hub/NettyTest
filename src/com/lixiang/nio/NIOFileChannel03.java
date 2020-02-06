package com.lixiang.nio;
import	java.io.FileOutputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Description //文件先读后写
 * @Author 李项
 * @Date 2020/2/3
 * @Version 1.0
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("a.txt");
        FileChannel channel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("b.txt");
        FileChannel channel2 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while(true){
            //清空缓冲区，重置
            byteBuffer.clear();
            int read = channel.read(byteBuffer);
            if(read == -1){  //表示读完
                break;
            }
            byteBuffer.flip();
            channel2.write(byteBuffer);

        }
        fileInputStream.close();
        fileOutputStream.close();
    }

}
