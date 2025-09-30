package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Ping extends Command {
    @Override
    public void validateCommand(List<String> args) throws Exception {
        if (!args.isEmpty()) {
            throw new Exception("Redis PING command requires 0 arg but found " + args.size() + " args");
        }
    }

    @Override
    public void executeCommand(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) throws Exception {
        try {
            validateCommand(clientRequest.getArgs());

            outputStream.write(ResponseConstructor.constructSimpleString("PONG"));
            outputStream.flush();
        } catch (IOException io) {
            throw new RuntimeException("I/O error while replying to client", io);
        }
    }
}
