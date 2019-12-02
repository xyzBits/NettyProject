package com.dongfang.netty.nio.buffer;

import java.nio.ByteBuffer;

public class NioByteBufferPutGet {
    public static void main(String[] args) {
        // 创建一个buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);

        // 类型化存放数据
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('东');
        buffer.putShort((short) 4);

        // 取出
        buffer.flip();
        // java.nio.BufferUnderflowException
        // 读取的顺序要与写入相同，不然会发生BufferUnderflowException
//        System.out.println("buffer.getShort() = " + buffer.getShort());
        System.out.println("buffer.getInt() = " + buffer.getInt());
        System.out.println("buffer.getLong() = " + buffer.getLong());
        System.out.println("buffer.getChar() = " + buffer.getChar());
        System.out.println("buffer.getShort() = " + buffer.getShort());
    }
}
