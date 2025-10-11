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
    public void executeCommand(OutputStream outputStream, ClientRequest clientRequest) throws Exception {
        try {
            String key = clientRequest.getArgs().getFirst();
            int removeElementsLen = 1;
            if (clientRequest.getArgs().size() > 1) {
                removeElementsLen = Integer.parseInt(clientRequest.getArgs().get(1));
            }

            List<String> values = RedisDB.INSTANCE.getKeyListDataStore().getData(key);
            byte[] response;
            if (values == null || values.isEmpty()) {
                response = ResponseConstructor.nullBulkString();
            } else if (removeElementsLen == 1) {
                String removedValue = values.getFirst();
                RedisDB.INSTANCE.getKeyListDataStore().updateData(key, new ArrayList<>(values.subList(1, values.size())));
                response = ResponseConstructor.constructBulkString(removedValue);
            } else {
                List<String> removedValues;
                if (removeElementsLen >= values.size()) {
                    removedValues = values;
                    RedisDB.INSTANCE.getKeyListDataStore().updateData(key, new ArrayList<>());
                } else {
                    removedValues = values.subList(0, removeElementsLen);
                    RedisDB.INSTANCE.getKeyListDataStore().updateData(key, new ArrayList<>(values.subList(removeElementsLen, values.size())));
                }
                response = ResponseConstructor.constructArrayResponse(removedValues);
            }

            outputStream.write(response);
            outputStream.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
