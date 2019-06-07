package com.xunpu.client.SingleThreadChat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SingleThreadServer {
    public static void main(String[] args) throws IOException {
        //创建ServerSocket对象
        ServerSocket serverSocket=new ServerSocket(6666);
        System.out.println("服务器端启动，等待连接。。。。");

        //接收Socket对象
        Socket client=serverSocket.accept();
        System.out.println("连接到客户端，它的端口地址为"+client.getRemoteSocketAddress());

        //接收信息
        InputStream clientInput=client.getInputStream();
        Scanner scanner=new Scanner(clientInput);
        //发送信息
        OutputStream clientOutput=client.getOutputStream();
        while(scanner.hasNext()){
            String message = scanner.nextLine();
            System.out.println("客户端对服务端说：" + message);
           //发送消息
            OutputStreamWriter writer=new OutputStreamWriter(clientOutput);
            writer.write("你好，客户端。正在处理您的信息\n");
            writer.flush();
        }
        scanner.close();
        clientInput.close();
        clientOutput.close();

    }


}

