package com.java.redis.factories.commands;

import com.java.redis.commands.Command;
import com.java.redis.commands.Ping;

public class PingFactory extends CommandFactory{
    @Override
    protected Command createCommand() {
        return new Ping();
    }
}
