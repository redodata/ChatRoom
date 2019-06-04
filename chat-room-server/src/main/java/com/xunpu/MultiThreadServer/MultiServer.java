package com.xunpu.MultiThreadServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiServer {
    public static void main(String[] args) {
        ExecutorService executorService=Executors.newFixedThreadPool(100);
        int port=8888;
        if(args.length>0){
            port=Integer.parseInt(args[0]);
        }else{
            System.out.println("使用默认参数"+"port="+port);
        }
        try {
            //创建ServerSocket对象
            ServerSocket serverSocket=new ServerSocket(port);
            System.out.println("服务器启动 "+serverSocket.getLocalSocketAddress());
            System.out.println("等待客户端连接。。。。。");
            System.out.println("注册请输入userName:用户名");
            System.out.println("私聊请输入privateChat:要私聊的用户名:私聊内容");
            System.out.println("群聊请输入groupChat:群聊内容");
            System.out.println("退出请输入bye");

            //为什么要使用newFixedThreadPool方法创建线程池？
            //设置最多同时有10个用户在线

            while(true){
                //接收客户端对象
                Socket client=serverSocket.accept();
                executorService.submit(new ExecutorClient(client));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
