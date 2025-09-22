package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;

import static com.java.redis.utils.Constants.END_OF_LINE;

public class NonSupportedCommand extends Command {

    public NonSupportedCommand(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) {
        this.outputStream = outputStream;
        this.clientRequest = clientRequest;
        this.redisDB = redisDB;
    }

    @Override
    public void executeCommand() {
        try {
            outputStream.write(ResponseConstructor.constructErrorResponse(" unknown command " + this.clientRequest.getNonSupportedCommandName()));
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("I/O error while replying to client", e);
        }
    }
}
