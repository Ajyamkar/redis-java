package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.OutputStream;
import java.util.List;

public class Set extends Command {

    public Set(OutputStream outputStream, ClientRequest clientRequest,  RedisDB redisDB){
        this.outputStream = outputStream;
        this.clientRequest = clientRequest;
        this.redisDB = redisDB;
    }

    @Override
    public void executeCommand() throws Exception {
        try {
            List<String> args = this.clientRequest.getArgs();

            validateCommand(SupportedCommand.SET);

            redisDB.store(args.getFirst(), args.getLast());
            outputStream.write(ResponseConstructor.constructSimpleString("OK"));
            outputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException("I/O error while replying to client", e);
        }
    }
}
