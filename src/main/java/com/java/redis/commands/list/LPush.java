package com.java.redis.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.SupportedCommand;
import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.OutputStream;
import java.util.List;

public class LPush extends Command {
    public LPush(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) {
        this.outputStream = outputStream;
        this.clientRequest = clientRequest;
        this.redisDB = redisDB;
    }

    @Override
    public void executeCommand() {
        try {
            validateCommand(SupportedCommand.LPUSH);
            List<String> args = this.clientRequest.getArgs();
            String key = args.getFirst();
            List<String> values = args.subList(1, args.size());

            int response = redisDB.getKeyListDataStore().storeKeyListByPrepend(key, values);
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
