package com.java.redis;

import com.java.redis.commands.*;
import com.java.redis.database.RedisDB;
import com.java.redis.factories.commands.*;
import com.java.redis.factories.commands.list.*;
import com.java.redis.models.ClientRequest;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RedisCommandsFactory {
    private static final Map<SupportedCommand, CommandFactory> registry = new HashMap<>();

    static {
        registry.put(SupportedCommand.PING, new PingFactory());
        registry.put(SupportedCommand.ECHO, new EchoFactory());
        registry.put(SupportedCommand.SET, new SetFactory());
        registry.put(SupportedCommand.GET, new GetFactory());
        registry.put(SupportedCommand.RPUSH, new RPushFactory());
        registry.put(SupportedCommand.LRANGE, new LRangeFactory());
        registry.put(SupportedCommand.LPUSH, new LPushFactory());
        registry.put(SupportedCommand.LLEN, new LLenFactory());
        registry.put(SupportedCommand.LPOP, new LPopFactory());
    }


    public static CommandFactory getCommand(ClientRequest clientRequest, OutputStream outputStream, RedisDB redisDB) {
        try {
            CommandFactory commandFactory = registry.get(clientRequest.getCommand());
            return Objects.requireNonNullElseGet(commandFactory, NonSupportedCommandFactory::new);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported Redis command");
        }
    }
}
