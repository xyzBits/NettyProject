package com.dongfang.fluent.ch1_intro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EchoPlayer {
    private String echo(String msg) {
        return "echo: " + msg;
    }

    public void talk() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg;

        while ((msg = br.readLine()) != null) {
            System.out.println(echo(msg));
            if ("bye".equalsIgnoreCase(msg)) break;
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoPlayer().talk();
    }
}
