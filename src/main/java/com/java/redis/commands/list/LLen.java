package com.java.redis.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.CommandOptions;
import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class LLen extends Command {
    @Override
    public void validateCommand(List<String> args) throws Exception {
        if(args.size() < CommandOptions.LLEN_DEFAULT_ARGS_SIZE){
            throw new Exception("Redis LLEN command requires minimum 1 args but found " + args.size() + " args");
        }
    }

    @Override
    public void executeCommand(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) throws IOException {
        try {
            List<String> args = clientRequest.getArgs();
            String key = args.getFirst();
            validateCommand(args);

            List<String> values = redisDB.getKeyListDataStore().getData(key);
            outputStream.write(ResponseConstructor.constructIntegerReply(values.size()));
        } catch (Exception e) {
            outputStream.write(ResponseConstructor.constructIntegerReply(0));
        }
        outputStream.flush();
    }
}
