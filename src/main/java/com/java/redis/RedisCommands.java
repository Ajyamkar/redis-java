package com.java.redis;

import com.java.redis.commands.Command;
import com.java.redis.commands.Echo;
import com.java.redis.commands.NonSupportedCommand;
import com.java.redis.models.ClientRequest;

import java.io.OutputStream;

public class RedisCommands {

    public static Command getCommand(ClientRequest clientRequest, OutputStream outputStream) {
        try {
            return switch (clientRequest.getCommand()) {
                case ECHO -> new Echo(outputStream, clientRequest);
                case null, default -> new NonSupportedCommand(outputStream, clientRequest);
            };
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported Redis command");
        }
    }
}
