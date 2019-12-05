package com.dongfang.fluent.ch2_socket;

import java.io.IOException;
import java.net.Socket;

/**
 * 在C/S通信模式中，Client要主动地创建与Server连接的Socket，Server收到Client
 * 的连接请求，与会创建与Client连接的Socket，Socket可以看作是通信连接两端的收发器，
 * Client Server都通过Socket来收发数据
 *
 *      构造器：除了无参构造器，其他构造方法都会试图建立与服务器的连接，如果连接成功，
 *      就返回Socket对象，如果因为某些原因连接失败，就会抛出IOException
 */
public class PortScanner {
    public static void main(String[] args) {
        String host = "localhost";
        scan(host);
    }

    private static void scan(String host) {
        for (int port = 1; port < 1024; port++) {
            try (Socket socket = new Socket(host, port)) {
                System.out.println("There is a Server on port " + port);
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
    }
}
