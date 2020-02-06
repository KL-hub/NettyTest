package com.lixiang.nio;

import java.nio.ByteBuffer;

/**
 * @Description //TODO
 * @Author 李项
 * @Date 2020/2/3
 * @Version 1.0
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        //创建一个Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte) i);
        }
        //读取
        byteBuffer.flip();
        ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();
        System.out.println(byteBuffer1.getClass());
        while(byteBuffer1.hasRemaining()){
            System.out.println(byteBuffer1.get());
        }
        byteBuffer1.put((byte) 64);
    }
}
