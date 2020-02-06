package com.lixiang.nio;

import jdk.nashorn.internal.ir.CallNode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/3
 * @Version 1.0
 *
 * 1、MappedByteBuffer可以让文件在内存中修改，操作系统不需要拷贝一次
 */
public class MappedByteBuffer {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccession = new RandomAccessFile("a.txt", "rw");
        FileChannel channel = randomAccession.getChannel();
        /**
         *
         * 参数1:使用的是读写模式
         * 参数2：0：可以直接修改的起始位置
         * 参数3：5:  是映射到内存的大小，即将a.txt的多少个字节映射到内存
         * 可以直接修改的范围就是0-5
         * 实际类型是DirectByteBuffer
         */
        java.nio.MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0,(byte)'0');
        map.put(1,(byte)'1');
        map.put(2,(byte)'2');
        map.put(3,(byte)'3');
        map.put(4,(byte)'4');
       // map.put(5,(byte)'5');
        randomAccession.close();
    }
}
