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
import java.util.Map;

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

            List<String> values = redisDB.getKeyListDataStore().getData(key);

            if (values==null|| (start>stop && stop>0) || start>=values.size()){
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
        // Out-of-range indexes do not produce an error.
        // negative indices and indices beyond bounds are mapped (clamped) to valid ranges, rather than rejecting or erroring.

        int len = values.size();

        // 1. Adjust negatives
        if (start < 0) {
            start = len + start;
        }
        if (end < 0) {
            end = len + end;
        }

        // 2. Clamp values
        // If negative results after adjustment go below zero → clamp to zero.
        if (start < 0) {
            start = 0;
        }

        // If stop is larger than the actual end of the list, Redis will treat it like the last element of the list
        if (end >= len) {
            end = len - 1;
        }

        // As per Redis LRANGE If start is larger than the end of the list, an empty list is returned
        if (start >= len || start > end) {
            return new ArrayList<>();
        }

        // 5. Return correct slice (end + 1) as sublist 2 parameter is exclusive
        return new ArrayList<>(values.subList(start, end + 1));
    }
}
