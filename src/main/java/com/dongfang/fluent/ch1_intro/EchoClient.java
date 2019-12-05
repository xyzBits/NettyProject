package com.dongfang.fluent.ch1_intro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient extends Echo {
    private Socket client;
    private String host;
    private int port;

    public EchoClient(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        client = new Socket(this.host, this.port);
        System.out.println("客户端启动启动");
    }

    public void talk() {
        try (BufferedReader reader = getReader(client);
             PrintWriter writer = getWriter(client);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            String msg;
            while ((msg = console.readLine()) != null) {
                writer.println(msg);
                System.out.println(reader.readLine());
                if ("bye".equalsIgnoreCase(msg)) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoClient("localhost", 9999).talk();
    }
}
