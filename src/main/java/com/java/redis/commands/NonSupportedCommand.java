package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class NonSupportedCommand extends Command {

    @Override
    public void validateCommand(List<String> args) throws Exception {
        return;
    }

    @Override
    public void executeCommand(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) {
        try {
            outputStream.write(ResponseConstructor.constructErrorResponse(" unknown command " + clientRequest.getNonSupportedCommandName()));
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("I/O error while replying to client", e);
        }
    }
}
