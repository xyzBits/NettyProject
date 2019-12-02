package com.dongfang.netty.nio.buffer;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        // 举例说明buffer的使用
        // 创建一个buffer，大小为5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        System.out.println("intBuffer = " + intBuffer);

        // 向buffer中存放数据
//        intBuffer.put(10);
//        intBuffer.put(11);
//        intBuffer.put(12);
//        intBuffer.put(13);
//        intBuffer.put(14);

        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 如何从buffer读取数据
        // 将buffer转换，读写切换
        intBuffer.flip();

        /*
        读取数据不能超过5
        *     public Buffer flip() {
        limit = position;
        position = 0;
        mark = -1;
        return this;
    }
        * */

        // 设置后从位置1开始读取
        intBuffer.position(1);

        // 1 2不能超过3
        intBuffer.limit(3);
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

    }
}
