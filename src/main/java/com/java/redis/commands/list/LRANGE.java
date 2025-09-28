package com.java.redis.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.SupportedCommand;
import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.utils.ResponseConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class LRANGE extends Command {
    public LRANGE(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) {
        this.outputStream = outputStream;
        this.clientRequest = clientRequest;
        this.redisDB = redisDB;
    }

    @Override
    public void executeCommand() {
        try {
            validateCommand(SupportedCommand.LRANGE);

            List<String> args = this.clientRequest.getArgs();
            String key = args.getFirst();
            int start = Integer.parseInt(args.get(1));
            int stop = Integer.parseInt(args.get(2));

            List<String> values = redisDB.getKeyList(key);

            if (values==null|| start>stop || start>=values.size()){
                outputStream.write(ResponseConstructor.constructArrayResponse(new ArrayList<>()));
                outputStream.flush();
                return;
            }

            if (stop >= values.size()){
                stop= values.size() - 1;
            }

            outputStream.write(ResponseConstructor.constructArrayResponse(getValuesSubList(values, start, stop)));
            outputStream.flush();

        } catch (Exception io){
            throw new RuntimeException("I/O error while replying to client", io);
        }
    }

    private List<String> getValuesSubList(List<String> values, int start, int end) {
        ArrayList<String> subList = new ArrayList<>();
        for (int i = start; i <=end ; i++) {
            subList.add(values.get(i));
        }
        return subList;
    }
}
