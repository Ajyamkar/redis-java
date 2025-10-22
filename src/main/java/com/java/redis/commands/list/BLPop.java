package com.java.redis.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.CommandOptions;
import com.java.redis.database.RedisDB;
import com.java.redis.database.datastores.KeyListDataStore;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BLPop extends Command {
    @Override
    public void validateCommand(List<String> args) throws Exception {
        if (args.size() < CommandOptions.BLPOP_DEFAULT_ARGS_SIZE) {
            throw new Exception("Redis BLPOP command requires 1 arg but found " + args.size() + " args");
        }
    }

    @Override
    public void executeCommand(OutputStream outputStream, ClientRequest clientRequest) throws Exception {
        System.out.println("clientRequest.getArgs(): " + clientRequest.getArgs());
        List<String> args = clientRequest.getArgs();
        List<String> keysToPop = args.subList(0, args.size() - 1);
        System.out.println("keysToPop: " + keysToPop);

        long timeout = Long.parseLong(args.getLast());
        long deadline = System.currentTimeMillis() + (timeout * 1000);

        if (timeout==0){
            while (!foundNonEmptyKey(keysToPop, outputStream)){}
            return;

        } else {
            while (System.currentTimeMillis() < deadline) {
            }
        }
        if (!foundNonEmptyKey(keysToPop, outputStream)) {
            outputStream.write(ResponseConstructor.nullBulkString());
        }
        outputStream.flush();
    }

    private boolean foundNonEmptyKey(List<String> keysToPop, OutputStream outputStream) throws IOException {
        KeyListDataStore keyListDataStore = RedisDB.INSTANCE.getKeyListDataStore();

        for (String key : keysToPop) {
            ArrayList<String> values = keyListDataStore.getData(key);
            if (values != null && !values.isEmpty()) {
                String removedValue = values.getFirst();
                keyListDataStore.updateData(key, new ArrayList<>(values.subList(1, values.size())));
                outputStream.write(ResponseConstructor.constructArrayResponse(List.of(key, removedValue)));
                return true;
            }
        }
        return false;
    }
}
