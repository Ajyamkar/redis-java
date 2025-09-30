package com.java.redis.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.CommandOptions;
import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.OutputStream;
import java.util.List;

public class RPush extends Command {

    @Override
    public void validateCommand(List<String> args) throws Exception {
        if (args.size() < CommandOptions.RPUSH_DEFAULT_ARGS_SIZE){
            throw new Exception("Redis RPUSH command requires minimum 2 args but found " + args.size() + " args");
        }
    }

    @Override
    public void executeCommand(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) throws Exception {
        try {
            List<String> args = clientRequest.getArgs();
            validateCommand(args);
            String key = args.getFirst();
            List<String> values = args.subList(1, args.size());
            int response = redisDB.getKeyListDataStore().storeKeyList(key, values);
            if(response == -1) {
                outputStream.write(ResponseConstructor.constructErrorResponse("Key "+key+" holds a value that is not a list."));
            } else {
                outputStream.write(ResponseConstructor.constructIntegerReply(response));
            }
            outputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
