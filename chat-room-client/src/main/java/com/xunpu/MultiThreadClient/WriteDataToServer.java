package com.xunpu.client.MultiThreadClient;

 import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
public class WriteDataToServer extends Thread {
    private final Socket client;

    WriteDataToServer(Socket client) {
        this.client = client;
    }

    public void run() {
        OutputStream clientOutput;
        try {
            clientOutput =this.client.getOutputStream();
            OutputStreamWriter writer=new OutputStreamWriter(clientOutput);
            Scanner scanner=new Scanner(System.in);
            String message;
            while(true){
               System.out.println("请输入信息：");
               message=scanner.nextLine();
               writer.write(message+"\n");
               writer.flush();
               if(message.equals("bye")){
                   client.close();
                   break;
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
