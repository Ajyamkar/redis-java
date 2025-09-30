package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Echo extends Command {

    @Override
    public void validateCommand(List<String> args) throws Exception {
        if (args.size() != CommandOptions.ECHO_DEFAULT_ARGS_SIZE) {
            throw new Exception("Redis ECHO command requires 1 arg but found " + args.size() + " args");
        }
    }

    @Override
    public void executeCommand(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) throws Exception {
        try {
            validateCommand(clientRequest.getArgs());

            outputStream.write(ResponseConstructor.constructBulkString(clientRequest.getArgs().getFirst()));
            outputStream.flush();
        } catch (IOException io) {
            throw new RuntimeException("I/O error while replying to client", io);
        }
    }
}
