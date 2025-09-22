package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;

public class Ping extends Command {

    public Ping(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) {
        this.outputStream = outputStream;
        this.clientRequest = clientRequest;
        this.redisDB = redisDB;
    }

    @Override
    public void executeCommand() throws Exception {
        try {
            validateCommand(SupportedCommand.PING);

            outputStream.write(ResponseConstructor.constructSimpleString("PONG"));
            outputStream.flush();
        } catch (IOException io) {
            throw new RuntimeException("I/O error while replying to client", io);
        }
    }
}
