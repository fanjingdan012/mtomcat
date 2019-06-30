package com.fjd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

public class WebServer {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started.");
        while(!serverSocket.isClosed()){
            Socket request = serverSocket.accept();
            threadPool.execute(()->{
                try{
                    InputStream inputStream = request.getInputStream();
                    System.out.println("receive request:");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
                    String msg = null;
                    StringBuilder requestInfo = new StringBuilder();
                    while((msg = reader.readLine())!=null){
                        if(msg.length()==0){
                            break;
                        }
                        requestInfo.append(msg);
                    }
                    System.out.println(requestInfo);

                }catch(IOException e){

                }
            });
        }


    }
}
