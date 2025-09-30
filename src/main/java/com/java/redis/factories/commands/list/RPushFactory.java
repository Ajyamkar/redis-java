package com.java.redis.factories.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.list.RPush;
import com.java.redis.factories.commands.CommandFactory;

public class RPushFactory extends CommandFactory {
    @Override
    protected Command createCommand() {
        return new RPush();
    }
}
