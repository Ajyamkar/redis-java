package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;

import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

public abstract class Command {

    public abstract void validateCommand(List<String> args) throws Exception;

    public abstract void executeCommand(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) throws Exception;
}
