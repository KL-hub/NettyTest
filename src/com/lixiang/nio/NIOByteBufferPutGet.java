package com.lixiang.nio;

import java.nio.ByteBuffer;

/**
 * @Description //
 * @Author 李项
 * @Date 2020/2/3
 * @Version 1.0
 *
 *  1、ByteBuffer支持类型化的put和get，Put放入的是什么数据类型，get就应该使用相应的数据类型取出
 *  否则可能有BufferUnderflowException异常
 *  2、可以将一个普通Buffer转成只读Buffer
 *  3、NIO还提供了MappedByteBuffer,可以让文件直接在内存（堆外的内存）中进行修改，而如何同步到文件由NIO来完成。
 *
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        //创建一个Buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('李');

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());

    }
}
