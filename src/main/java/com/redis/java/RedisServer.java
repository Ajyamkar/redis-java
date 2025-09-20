package com.redis.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RedisServer {
    private final Socket clientSocket;

    public RedisServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void execute() throws IOException {
        System.out.println("Current Thread: " + Thread.currentThread().getName() + " started: " + Thread.currentThread().getState() + " and is listening for request..");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream outputStream = clientSocket.getOutputStream();) {
            String content;
//            int i=0;
//            int messageLength = 1;
            while ((content = reader.readLine()) != null) {
                System.out.println("content from thread: "+ Thread.currentThread() +": " + content);
//                if(content.substring(content.length()-2).contains("*")){
//                    messageLength=Integer.parseInt(content.substring(content.length()-1)) * 2;
//                }
                if ("PING".equalsIgnoreCase(content)) {
                    outputStream.write("+PONG\r\n".getBytes());
                    outputStream.flush();
//                    i=0;
                }
//                i++;
            }
        }
    }
}
