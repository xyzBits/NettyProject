package com.dongfang.fluent.ch1_intro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Java的网络程序都建立在TCP/IP协议的基础上，致力于实现应用层，传输层向应用层提供了套接字Socket接口，
 * Socket封装了下层的数据传输细节，应用层的程序通过Socket来建立与远程主机的连接，以及进行数据传输
 *
 * 站在应用层的角度，两个进程之间的一次通信过程从建立连接开始，接着交换数据，到断开连接结束，套接字可以看作
 * 通信线路两端的收发器，进程通过套接字来收发数据
 *              进程A <------>Socket<------>TCP连接<------>Socket<------>进程B
 *
 *  Java中，有3种套接字类，Socket ServerSocket DatagramSocket，其中Socket ServerSocket建立在TCP协议的基础上，
 *  DatagramSocket建立在UDP的基础上，Java网络程序都采用客户/服务器通信模式
 *
 *      ServerSocket server = new ServerSocket(9999)
 *      服务器程序通过一直监听端口，来接收客户端程序的连接请求，在服务器程序中，需要首先创建一个ServerSocket对象，
 *      在构造方法中指定监听的端口
 *
 *      ServerSocket的构造方法负责在操作系统中把当前进程注册为服务器进程。服务器接下来调用ServerSocket对象的
 *      accept()方法，该方法一直监听端口，等待客户端的连接请求，如果收到一个连接请求，accept()方法就返回一个Socket
 *      对象，这个Socket对象就与客户端的Socket对象形成了一条通信线路
 * */
public class EchoServer extends Echo {
    private int port;
    private ServerSocket server;


    public EchoServer(int port) throws IOException {
        this.port = port;
        server = new ServerSocket(this.port);
        System.out.println("服务器启动");

    }

    private String echo(String msg) {
        return "echo: " + msg;
    }


    public void service() {
        while (true) {
            try (Socket socket = server.accept();
                 BufferedReader reader = getReader(socket);
                 PrintWriter writer = getWriter(socket)) {
                System.out.println("New connection accepted " + socket.getInetAddress() + ": " + socket.getPort());

                String msg;
                while ((msg = reader.readLine()) != null) {
                    System.out.println(msg);
                    writer.println(echo(msg));
                    if ("bye".equalsIgnoreCase(msg)) break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoServer(9999).service();
    }
}
