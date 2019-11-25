package com.dongfang.netty.nio;

import java.nio.ByteBuffer;

public class ReadOnlyBuffer {
    public static void main(String[] args) {
        // 创建一个buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (byte i = 0; i < 64; i++) {
            buffer.put(i);
        }

        // 读取
        buffer.flip();

        // 得到一个只读的buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println("readOnlyBuffer.getClass() = " + readOnlyBuffer.getClass());

        // 读取
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println("readOnlyBuffer.get() = " + readOnlyBuffer.get());
        }

        // java.nio.ReadOnlyBufferException
        readOnlyBuffer.put((byte) 99);
    }
}
