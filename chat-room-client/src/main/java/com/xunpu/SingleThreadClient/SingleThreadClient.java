package com.xunpu.SingleThreadClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SingleThreadClient {
    public static void main(String[] args) throws IOException {
      //创建Socket对象
        Socket client=new Socket("127.0.0.1",6666);
        //服务器端先接收信息，再发送信息。
        //客户端为什么要先发送信息，再接收？
        //发送信息
        OutputStream clientOutput=client.getOutputStream();
        OutputStreamWriter writer=new OutputStreamWriter(clientOutput);
        writer.write("你好，服务器端！\n");
        writer.flush();
        //接收信息
        InputStream clientInput=client.getInputStream();
        Scanner scanner=new Scanner(clientInput);
        String message=scanner.nextLine();
        System.out.println("服务端对客户端说："+message);


        //关闭
        clientOutput.close();
        clientInput.close();
        client.close();
    }

}



/*
 public class SingleThreadClient {

    public static void main(String[] args) throws IOException {

        //1.创建客户端，连接到服务器。

        int port=6666;

        if(args.length>0){

            port=Integer.parseInt(args[0]);

        }else{

            System.out.println("端口参数不正确，采用默认参数");

        }

        String host="127.0.0.1";

        if(args.length>1){

            host=args[1];

        }

        Socket clientSocket=new Socket(host,port);

        //2.接收，发送数据

        //2.1发送数据

        OutputStream clientOutput=clientSocket.getOutputStream();

        OutputStreamWriter writer=new OutputStreamWriter(clientOutput);

        writer.write("你好，我是客户端\n");//写到输出流中，服务器端读取到的数据

        writer.flush();

        //2.2接收数据

        InputStream clientInput=clientSocket.getInputStream();

        Scanner scanner=new Scanner(clientInput);

        String serverData=scanner.nextLine();

        System.out.println("来自服务器端的数据："+serverData);

        //3.客户端的关闭

        clientOutput.close();

        clientInput.close();

        clientSocket.close();

    }

}
*/
