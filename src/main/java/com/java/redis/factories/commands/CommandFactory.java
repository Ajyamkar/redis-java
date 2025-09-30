package com.java.redis.factories.commands;

import com.java.redis.commands.Command;
import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;

import java.io.OutputStream;

public abstract class CommandFactory {
    public void executeCommand(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) throws Exception {
        Command command = createCommand();
        command.executeCommand(outputStream, clientRequest, redisDB);
    }

    protected abstract Command createCommand();
}
