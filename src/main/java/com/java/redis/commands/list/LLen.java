package com.java.redis.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.SupportedCommand;
import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class LLen extends Command {
    public LLen(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) {
        this.outputStream = outputStream;
        this.clientRequest = clientRequest;
        this.redisDB = redisDB;
    }

    @Override
    public void executeCommand() throws IOException {
        try {
            validateCommand(SupportedCommand.LLEN);
            List<String> args = clientRequest.getArgs();
            String key = args.getFirst();

            List<String> values = this.redisDB.getKeyListDataStore().getData(key);
            outputStream.write(ResponseConstructor.constructIntegerReply(values.size()));
        } catch (Exception e) {
            outputStream.write(ResponseConstructor.constructIntegerReply(0));
        }
        outputStream.flush();
    }
}
