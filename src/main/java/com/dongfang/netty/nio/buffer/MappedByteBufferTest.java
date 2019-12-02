package com.dongfang.netty.nio.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer直接让文件在内存中修改，操作系统不需要拷贝
 */
public class MappedByteBufferTest {
    private static final String filePath = "D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\NettyProject\\src\\main\\resources\\testfile\\niofile01.txt";

    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(filePath, "rw");

        // 获取对就的文件通道
        FileChannel rafChannel = raf.getChannel();

        /**
         * arg1 FileChannel.MapMode.READ_WRITE 使用读写模式
         * arg2 可以修改的起始位置
         * arg3 映射到内存的大小，文件的第几个位置映射到操作系统内存，即文件的多少人字节映射到内存，
         *      即可以直接修改的范围
         *      可以修改的字节的个数
         *
         *      实际类型是 java.nio.DirectByteBuffer
         */
        MappedByteBuffer mappedByteBuffer = rafChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        System.out.println("mappedByteBuffer.getClass() = " + mappedByteBuffer.getClass());
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
        // java.lang.IndexOutOfBoundsException
        // 5代表只修改5个
        mappedByteBuffer.put(5, (byte) 'Y');

        rafChannel.close();

        System.out.println("修改成功");

    }
}
