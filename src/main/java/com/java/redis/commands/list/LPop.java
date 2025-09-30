package com.java.redis.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.CommandOptions;
import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class LPop extends Command {
    @Override
    public void validateCommand(List<String> args) throws Exception {
        if (args.size() < CommandOptions.LPOP_DEFAULT_ARGS_SIZE) {
            throw new Exception("Redis LPOP command requires 1 arg but found " + args.size() + " args");
        }
    }

    @Override
    public void executeCommand(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) throws Exception {
        try {
            String key = clientRequest.getArgs().getFirst();
            List<String> values = redisDB.getKeyListDataStore().getData(key);
            if (values == null || values.isEmpty()) {
                outputStream.write(ResponseConstructor.nullBulkString());
            } else {
                String removedValue = values.getFirst();
                redisDB.getKeyListDataStore().updateData(key, new ArrayList<>(values.subList(1,values.size())));
                outputStream.write(ResponseConstructor.constructBulkString(removedValue));
            }
            outputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
