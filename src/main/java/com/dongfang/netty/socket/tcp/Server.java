package com.dongfang.netty.socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2000);
        System.out.println("服务器准备就绪");
        System.out.println("服务器信息 " + server.getInetAddress() + ", port " + server.getLocalPort());

        // 等待客户端连接
        for (; ; ) {
            // 得到客户端
            Socket client = server.accept();
            // 构建客户端异步线程
            ClientHandler clientHandler = new ClientHandler(client);
            // 启动线程进行处理
            clientHandler.start();
        }

    }


    /**
     * 客户端消息处理
     */
    private static class ClientHandler extends Thread {
        private Socket socket;
        private boolean flag = true;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("新客户端连接： " + socket.getInetAddress() + ", port " + socket.getPort());

            try {
                //得到打印流，用于数据输出，服务器回送数据
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                do {
                    // 客户端拿到一条数据
                    String str = socketInput.readLine();
                    if ("bye".equalsIgnoreCase(str)) {
                        flag = false;
                        // 回送
                        socketOutput.println("bye");
                    } else {
                        // 打印到屏幕，并回送数据长度
                        System.out.println(str);
                        System.out.println("回送" + str.length());
                    }
                } while (flag);

                socketInput.close();
                socketOutput.close();

            } catch (IOException e) {
                System.err.println("连接异常断开");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("客户商朝已退出" + socket.getInetAddress() + ", port " + socket.getPort());
        }
    }
}
