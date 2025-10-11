package com.java.redis;

import com.java.redis.factories.commands.CommandFactory;
import com.java.redis.models.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RedisServer implements Runnable {
    private final Socket clientSocket;
    private final ClientRequest clientRequest;

    public RedisServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.clientRequest = new ClientRequest();
    }

    @Override
    public void run() {
        respondToClientRequest();
    }

    private void respondToClientRequest() {
        System.out.println("Current Thread: " + Thread.currentThread().getName()
                + " started: " + Thread.currentThread().getState()
                + " and is listening for request..");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream outputStream = clientSocket.getOutputStream();
        ) {
            while (readClientRequest(reader)) {
                try {
                    CommandFactory commandFactory = RedisCommandsFactory.getCommand(clientRequest, outputStream);
                    commandFactory.executeCommand(outputStream, clientRequest);
                } catch (Exception e) {
                    System.out.println("Exception While running the command: " + this.clientRequest.getCommand() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private boolean readClientRequest(BufferedReader reader) throws IOException {
        String firstLine = reader.readLine();
        if (firstLine == null) {
            return false; // client disconnected
        }

        if (!firstLine.startsWith("*")) {
            throw new IOException("Invalid command - Invalid RESP format: Expected array start '*'");
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

            System.out.println("args: " + args);
            this.clientRequest.setArgs(args);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }
}
