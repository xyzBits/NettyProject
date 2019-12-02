package com.dongfang.netty.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel03 {
    private static final String filePath = "D:\\ubuntu\\learn\\JavaWeb\\MavenProject\\maven03\\NettyProject\\src\\main\\resources\\testfile\\niofile01.txt";

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        FileChannel inputChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream(filePath.replace("01", "02"));
        FileChannel outputChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {
            // 这里有一个重要的操作，复位
            byteBuffer.clear(); //清空buffer中的数据
            int len = inputChannel.read(byteBuffer);
            System.out.println("len = " + len);
            if (len == -1) {
                break;
            }
            // 将buffer中的数据写入到outputChannel
            byteBuffer.flip();
            outputChannel.write(byteBuffer);
        }

        fileOutputStream.close();
        fileInputStream.close();


    }
}
