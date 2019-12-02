package com.dongfang.netty.nio.channel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel01 {

    private static final String filePath = "D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\NettyProject\\src\\main\\resources\\testfile\\niofile01.txt";
    public static void main(String[] args) throws IOException {
        String str = "hello,东方";
        // 创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);

        // 通过fileOutputStream 获取对就的FileChannel
        // 这个Channel的真实类型是FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓冲区ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 将str放入到byteBuffer
        byteBuffer.put(str.getBytes());

        // 对byteBuffer 反转
        byteBuffer.flip();

        // 将byteBuffer的数据写入到fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();

    }
}
