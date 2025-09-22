import com.java.redis.RedisServer;
import com.java.redis.database.RedisDB;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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

            RedisDB redisDB = new RedisDB();

            // Continuous server running...
            while (true) {
                // Wait for connection from client.
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection");
                System.out.println("Redis Db" + redisDB.getFullData());

                executor.submit(() -> {
                    RedisServer redisServer = new RedisServer(clientSocket, redisDB);
                    redisServer.respondToClientRequest();
                });
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
