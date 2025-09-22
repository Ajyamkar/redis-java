package com.java.redis;

import com.java.redis.commands.*;
import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;

import java.io.OutputStream;

public class RedisCommands {

    public static Command getCommand(ClientRequest clientRequest, OutputStream outputStream,  RedisDB redisDB) {
        try {
            return switch (clientRequest.getCommand()) {
                case PING -> new Ping(outputStream, clientRequest, redisDB);
                case ECHO -> new Echo(outputStream, clientRequest, redisDB);
                case SET -> new Set(outputStream, clientRequest, redisDB);
                case GET -> new Get(outputStream, clientRequest, redisDB);
                case null, default -> new NonSupportedCommand(outputStream, clientRequest, redisDB);
            };
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported Redis command");
        }
    }
}
