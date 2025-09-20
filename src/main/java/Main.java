import com.redis.java.RedisServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        int port = 6379;
        try (ServerSocket serverSocket = new ServerSocket(port);
             ExecutorService executor = Executors.newFixedThreadPool(4)
        ) {
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            // Continuous server running...
            while (true){
                // Wait for connection from client.
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection");
                executor.submit(()-> {

                    RedisServer redisServer = new RedisServer(clientSocket);
                    try {
                        redisServer.execute();
                    } catch (IOException e) {
                        System.out.println("IOException calling redis server: " + e.getMessage());
                    }
                });
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
