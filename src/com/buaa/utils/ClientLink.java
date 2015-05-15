package com.buaa.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.buaa.comman.MessageType;

import net.sf.json.JSONObject;

public class ClientLink {
    private Socket socket = null;
    private BufferedReader br;
    private InputStream in;
    private OutputStream out;
    private String inputString;

    public ClientLink(String address, int port) {
        try {
            socket = new Socket(address, port);
            out = socket.getOutputStream();
            in = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            close();
            inputString = MessageType.TIME_OUT + "{}";
        }
    }

    public void output(int sendType, JSONObject jsonObject) {
        if (out != null) {
            try {
                out.write(jsonToBytes(sendType, jsonObject));
            } catch (IOException e) {
                e.printStackTrace();
                inputString = MessageType.TIME_OUT + "{}";
            }
        }
    }

    public String input() {
        if (br != null) {
            try {
                inputString = br.readLine();
            } catch (IOException e) {
                inputString = MessageType.TIME_OUT + "{}";
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
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

}
