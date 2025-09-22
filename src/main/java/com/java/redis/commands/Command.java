package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;

import java.io.OutputStream;
import java.util.List;

public abstract class Command {
    public OutputStream outputStream;
    public ClientRequest clientRequest;
    public RedisDB redisDB;

    public void validateCommand(SupportedCommand command) throws Exception {
        List<String> args = this.clientRequest.getArgs();

        if (command == SupportedCommand.SET && args.size() != 2) {
            throw new Exception("Redis SET command requires 2 args but found " + args.size() + " args");
        }

        if (command == SupportedCommand.GET && args.size() != 1) {
            throw new Exception("Redis GET command requires 1 arg but found " + args.size() + " args");
        }

        if (command == SupportedCommand.ECHO && args.size() != 1) {
            throw new Exception("Redis ECHO command requires 1 arg but found " + args.size() + " args");
        }

        if (command == SupportedCommand.PING && !args.isEmpty()) {
            throw new Exception("Redis PING command requires 0 arg but found " + args.size() + " args");
        }
    }

    public void executeCommand() throws Exception {
    }
}
