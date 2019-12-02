package com.dongfang.netty.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class NioFileChannel04 {
    private static final String filePath = "D:\\ubuntu\\learn\\cpp\\yellow\\244.mp4";

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath.replace("244", "244cp"));

        FileChannel source = fileInputStream.getChannel();
        FileChannel dest = fileOutputStream.getChannel();

        // 使用transform完成
        //dest.transferFrom(source, 0, source.size());

        // 使用transferTo完成
        source.transferTo(0, source.size(), dest);
        source.close();
        dest.close();
        fileInputStream.close();
        fileOutputStream.close();



    }
}
