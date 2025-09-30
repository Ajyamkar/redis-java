package com.java.redis.factories.commands;

import com.java.redis.commands.Command;
import com.java.redis.commands.Get;

public class GetFactory extends CommandFactory{
    @Override
    protected Command createCommand() {
        return new Get();
    }
}
