package com.xunpu.client.MultiThreadClient;

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
                InputStream clientInput = this.client.getInputStream();
                Scanner scanner = new Scanner(clientInput);
             while(scanner.hasNext()){
                 String message=scanner.next();
                 System.out.println("来自服务器的消息："+message);
                 if(message.equals("bye")){
                     break;
                 }
             }
             clientInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}


