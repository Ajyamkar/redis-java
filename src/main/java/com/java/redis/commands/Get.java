package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Get extends Command {

    public Get(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB){
        this.outputStream = outputStream;
        this.clientRequest = clientRequest;
        this.redisDB = redisDB;
    }

    @Override
    public void executeCommand() throws Exception {
        try {
            List<String> args = this.clientRequest.getArgs();

            validateCommand(SupportedCommand.GET);
            String value = redisDB.getData(args.getFirst());

            outputStream.write(value==null ? ResponseConstructor.nullBulkString(): ResponseConstructor.constructBulkString(value));
            outputStream.flush();

        } catch (IOException io) {
            throw new RuntimeException("I/O error while replying to client", io);
        }
    }
}
