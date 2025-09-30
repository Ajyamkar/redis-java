package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Get extends Command {

    @Override
    public void validateCommand(List<String> args) throws Exception {
        if (args.size() != CommandOptions.GET_DEFAULT_ARGS_SIZE) {
            throw new Exception("Redis GET command requires 1 arg but found " + args.size() + " args");
        }
    }

    @Override
    public void executeCommand(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) throws Exception {
        try {
            List<String> args = clientRequest.getArgs();

            validateCommand(args);
            String value = redisDB.getKeyValueDataStore().getKeyValueData(args.getFirst());

            outputStream.write(ResponseConstructor.constructBulkString(value));
        } catch (RuntimeException e) {
            // Key doesn't exit in DB.
            outputStream.write(ResponseConstructor.nullBulkString());
        } catch (IOException io) {
            throw new RuntimeException("I/O error while replying to client", io);
        }

        outputStream.flush();
    }
}
