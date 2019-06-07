package com.xunpu.client.SingleThreadClient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
public class SingleThreadClient {
    public static void main(String[] args) throws IOException {
        //1.创建客户端，连接到服务器
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
        //自定义设置数据，与服务器端交互。
        Scanner sc=new Scanner(System.in);
        //2.2接收数据
        InputStream clientInput=clientSocket.getInputStream();
        while(sc.hasNext()) {
            String data = sc.nextLine();
            if(data.equals("bye")){
                writer.write("客户端退出了\n");
                writer.flush();
                break;
            }
            //因为服务器端按行读，因此写数据时要换行。否则服务器端读不到数据。
            writer.write(data + "\n");//写到输出流中，服务器端读取到的数据
            writer.flush();//为什么要刷新？因为网络传输数据有机制，因此要刷新。

            Scanner scanner=new Scanner(clientInput);
            String serverData=scanner.nextLine();
            System.out.println("来自服务器端的数据："+serverData);
        }
        sc.close();
        //3.客户端的关闭
        clientOutput.close();
        clientInput.close();
        clientSocket.close();
    }
}
