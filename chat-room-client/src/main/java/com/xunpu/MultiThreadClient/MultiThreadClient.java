package com.xunpu.MultiThreadClient;

import java.io.IOException;
import java.net.Socket;

public class MultiThreadClient {
    public static void main(String[] args) {
        String host="127.0.0.1";
        int port=8888;
        if(args.length>1){
            port=Integer.parseInt(args[0]);
            host=args[1];
        }else{
            System.out.println("使用默认参数：host="+host+" port="+port);
        }
        try {
            //创建Socket对象
            Socket client=new Socket(host,port);

            //发送数据
            (new WriteDataToServer(client)).start();
            //接收数据
            (new ReadDataFromServer(client)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
