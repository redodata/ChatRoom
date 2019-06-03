package com.xunpu.MultiThreadClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class ReadDataFromServer extends Thread{
    private final Socket client;

    ReadDataFromServer(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            InputStream clientInput=this.client.getInputStream();
            Scanner scanner=new Scanner(clientInput);
            String message;
           do {
                message = scanner.nextLine();
                System.out.println("来自服务器的消息：" + message);
            }while(!message.equals("bye"));
            clientInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
