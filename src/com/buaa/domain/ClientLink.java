package com.buaa.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientLink {
    private Socket client = null;
    private BufferedReader br;
    private InputStream in;
    private OutputStream out;

    public ClientLink(String address, int port) {
        try {
            client = new Socket(address, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void output(String json) {
        try {
            out = client.getOutputStream();
            out.write((json + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String input() {
        String str = null;
        try {
            in = client.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
