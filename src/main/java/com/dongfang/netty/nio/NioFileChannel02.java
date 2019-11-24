package com.dongfang.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel02 {
    private static final String filePath = "D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\NettyProject\\src\\main\\resources\\testfile\\niofile01.txt";

    public static void main(String[] args) throws IOException {
        // 创建文件的输入流
        FileInputStream fileInputStream = new FileInputStream(filePath);

        // 通过输入流对象，获取对应的FileChannel，实际类型FileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) new File(filePath).length());

        // 将通道的数据读入到buffer中
        fileChannel.read(byteBuffer);

        // 将byteBuffer中的字节数据转成String
        System.out.println("new String(byteBuffer.array()) = " + new String(byteBuffer.array()));

        fileInputStream.close();


    }
}
