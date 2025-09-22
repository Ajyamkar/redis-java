package com.java.redis;

import com.java.redis.commands.Command;
import com.java.redis.models.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class RedisServer {
    private final Socket clientSocket;
    private ClientRequest clientRequest;

    public RedisServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void respondToClientRequest() {
        System.out.println("Current Thread: " + Thread.currentThread().getName()
                + " started: " + Thread.currentThread().getState()
                + " and is listening for request..");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream outputStream = clientSocket.getOutputStream();
        ) {
            while (readClientRequest(reader, outputStream)) {
                try {
                    Command command = RedisCommands.getCommand(clientRequest, outputStream);
                    command.executeCommand();
                } catch (Exception e) {
                    System.out.println("Exception While running the command: " + this.clientRequest.getCommand() + "is" + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public boolean readClientRequest(BufferedReader reader, OutputStream outputStream) throws IOException {
        clientRequest = new ClientRequest();

        String firstLine = reader.readLine();
        if (firstLine == null) {
            return false; // client disconnected
        }

        // Example: *2 (array of 2 items: command + arg(s))
        int requestLength = Integer.parseInt(firstLine.substring(1));
        List<String> args = new ArrayList<>(requestLength - 1);

        // Read command length line (e.g. "$4")
        int commandLength = Integer.parseInt(reader.readLine().substring(1));

        // Read command string (e.g. "ECHO" or "PING")
        String command = reader.readLine();
        this.clientRequest.setRequestLength(requestLength);
        try {
            this.clientRequest.setCommand(command.toUpperCase());

            System.out.println("requestLength: " + requestLength);
            System.out.println("commandLength: " + commandLength);
            System.out.println("command: " + command);

            // Read remaining arguments (requestLength - 1)
            int argCount = requestLength - 1;
            while (argCount > 0) {
                String lenLine = reader.readLine(); // e.g. "$6"
                int argLen = Integer.parseInt(lenLine.substring(1));
                String value = reader.readLine();   // e.g. "orange"
                args.add(value);
                argCount--;
            }

            this.clientRequest.setArgs(args);
            System.out.println("args: " + args);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

}
