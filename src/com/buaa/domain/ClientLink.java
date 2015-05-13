package com.buaa.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import net.sf.json.JSONObject;

public class ClientLink {
    private Socket client = null;
    private BufferedReader br;
    private InputStream in;
    private OutputStream out;
    private String inputString;

    public ClientLink(String address, int port) {
        try {
            client = new Socket(address, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            inputString = "10018{}";
        }
    }

    public void output(int sendType, JSONObject jsonObject) {
        if (inputString == null) {
            try {
                out = client.getOutputStream();
                out.write(jsonToBytes(sendType, jsonObject));
            } catch (IOException e) {
                // e.printStackTrace();
                inputString = "10018{}";
            }
        }
    }

    public String input() {
        if (inputString == null) {
            try {
                in = client.getInputStream();
                br = new BufferedReader(new InputStreamReader(in));
                inputString = br.readLine();
            } catch (IOException e) {
                // e.printStackTrace();
                inputString = "10018{}";
            }
        }
        return inputString;
    }

    private byte[] jsonToBytes(int type, JSONObject jsonObject) {
        return (type + jsonObject.toString() + "\n").getBytes();
    }

    public void close() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (br != null) {
                br.close();
            }
            if (client != null) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
