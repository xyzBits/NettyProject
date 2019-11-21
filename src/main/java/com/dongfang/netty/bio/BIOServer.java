package com.dongfang.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {
        // 线程池机制
        // 1 创建线程池
        // 2 如果有客户端连接，就创建一个线程与之通信（单独写一个方法）

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();


        // 创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动");
        while (true) {
            System.out.println("线程信息为 id = " + Thread.currentThread().getId() + ", 名字为 " + Thread.currentThread().getName());

            // 监听，等待客户端连接
            System.out.println("等待连接......");
            Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端了");

            // 创建一个线程，与之通信，单独写一个方法
            newCachedThreadPool.execute(() -> {
                handler(socket);
            });

        }
    }

    // 编写一个handler方法，和客户端通信
    private static void handler(Socket socket) {

        System.out.println("线程信息为 id = " + Thread.currentThread().getId() + ", 名字为 " + Thread.currentThread().getName());
        try {
            byte[] bytes = new byte[1024];
            // 通过socket获取输入流
            InputStream inputStream = socket.getInputStream();

            //循环地读取客户端发送的数据
            while (true) {
                System.out.println("线程信息为 id = " + Thread.currentThread().getId() + ", 名字为 " + Thread.currentThread().getName());

                System.out.println("read .....");
                int len = inputStream.read(bytes);
                if (len != -1) {
                    System.out.println(new String(bytes, 0, len));
                } else {
                    break;
                }

            }
        } catch (IOException e) {
            System.err.println("e = " + e);
        } finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
