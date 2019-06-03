package com.xunpu.MultiThreadServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ExecutorClient implements Runnable {

    //为什么要使用ConcurrentHashMap?  因为当前有许多用户在执行业务（多线程），为了保证线程安全，使用ConcurrentHashMap。
    private final static Map<String,Socket> ONLINE_CLIENT=new ConcurrentHashMap<String, Socket>();

    private final Socket client;//确保是当前用户，不能被修改。

    ExecutorClient(Socket client){
        this.client=client;
    }

    public void run() {
        try {
        //获取用户输入的数据
            InputStream clientInput = this.client.getInputStream();
            Scanner scanner = new Scanner(clientInput);
             while(true) {
                 while (true) {
                     String inputMessage = scanner.nextLine();

                    //划分字符
                  String[] words = inputMessage.split("\\:");
                    //userName:name
                    if (inputMessage.startsWith("userName")) {

                        this.register(words[1], this.client);
                    }

                    //privateChat:toSayName:message
                    if (words[0].equals("privateChat")) {
                        String toSayName = words[1];
                        String message = words[2];
                        this.privateChat(toSayName, message);
                    }
                    //groupChat:message
                    if (words[0].equals("groupChat")) {
                        String message = words[1];
                        this.groupChat(words[1]);
                    }
                    //bye
                    if (words[0].equals("bye")) {
                        this.quit();
                        return;
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //打印当前在线人数和列表
    private void printUserName(){
        System.out.println("当前在线人数为："+ONLINE_CLIENT.size());
        System.out.println("在线人数列表为：");

        Iterator iterator=ONLINE_CLIENT.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Socket> entry = (Map.Entry)iterator.next();
            System.out.println((String)entry.getKey());
        }
    }
    //打印当前客户的姓名
    private String getCurrentName(){
        String currentName="";
        Iterator iterator2=ONLINE_CLIENT.entrySet().iterator();
        while(iterator2.hasNext()){
            Map.Entry<String, Socket> entry = (Map.Entry)iterator2.next();
            if(this.client.equals(entry.getValue())){
                currentName=(String)entry.getKey();
            }
        }
        return currentName;
    }
    //发送信息
    private void sendMessage(Socket toSayclient,String message){
        try {
            //client为要接收信息的客户端
            OutputStream clientOutput=toSayclient.getOutputStream();
            OutputStreamWriter writer=new OutputStreamWriter(clientOutput);
            writer.write(message+"\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //注册  userName:name
    private void register(String name,Socket client){
        //服务器端输出"name已注册"

            System.out.println(name+"已注册");
            ONLINE_CLIENT.put(name,client);
            printUserName();
        //向当前客户端输出“您已注册成功！"
        String message="您已注册成功!";
        sendMessage(this.client,message);

    }
    //私聊  privateChat:name;message
    private void privateChat(String toSayName,String message) {
        //首先根据toSayName，找到对方，然后再向对方发送消息
        String currentName = this.getCurrentName();

        Socket target = (Socket) ONLINE_CLIENT.get(toSayName);
        if (target != null) {
            this.sendMessage(target,currentName+"对你说："+message);
        }
    }

    //群聊 groupChat:message   给每个用户发送信息
    private void groupChat(String message){
        //
        String currentName=this.getCurrentName();
        Iterator iterator4=ONLINE_CLIENT.values().iterator();
        while(iterator4.hasNext()){
            Socket socket=(Socket)iterator4.next();
            //如果当前对象和遍历的对象是同一个，就跳过本次循环，即不再向自己发送信息。
            if(socket.equals(this.client)){
                continue;
            }
            this.sendMessage(socket,currentName+"说"+message);
        }


    }
    //退出
    private void quit(){
        String currentName=this.getCurrentName();
        //服务器输出：currentName退出
        System.out.println(currentName+"退出聊天室");
        //服务器向当前用户输出bye
        Socket socket=ONLINE_CLIENT.get(currentName);
        this.sendMessage(socket,"bye");
        ONLINE_CLIENT.remove(currentName);
        printUserName();
    }
}
