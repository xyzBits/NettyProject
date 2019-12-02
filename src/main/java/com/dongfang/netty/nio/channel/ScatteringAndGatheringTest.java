package com.dongfang.netty.nio.channel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering 将数据写入到buffer时，可以采用buffer数组，依次写入，分散
 * Gathering  从buffer中读取数据时，也可以采用buffer数组，按照顺序依次读取
 */
public class ScatteringAndGatheringTest {
    @Test
    public void testScatter() throws IOException {
        // 使用ServerSocketChannel 和 SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口都socket并启动
        serverSocketChannel.bind(inetSocketAddress);

        // 创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8; // 假定从客户端接收8个字节
        // 循环读取
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read; // 累计读取到的字节数
                System.out.println("byteRead = " + byteRead);
                // 输出 使用流打印，看看当前的这个buffer的position 和limit
                Arrays.asList(byteBuffers).stream().map(buffer -> "position = " + buffer.position()
                 + ", limit = " + buffer.limit()).forEach(System.out::println);
            }

            // 将所有的buffer反转 flip
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());

            // 读数据读出，显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }
            // 将所有的buffer进行复位操作
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.clear());
            System.out.println("byteRead = " + byteRead + ", byteWrite = " + byteWrite + ", messageLength = " + messageLength);
        }

    }
}
