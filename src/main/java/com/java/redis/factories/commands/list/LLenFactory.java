package com.java.redis.factories.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.list.LLen;
import com.java.redis.database.RedisDB;
import com.java.redis.factories.commands.CommandFactory;
import com.java.redis.models.ClientRequest;

import java.io.OutputStream;

public class LLenFactory extends CommandFactory {
    @Override
    protected Command createCommand() {
        return new LLen();
    }
}
