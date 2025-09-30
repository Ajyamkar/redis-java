package com.java.redis.factories.commands;

import com.java.redis.commands.Command;
import com.java.redis.commands.NonSupportedCommand;

public class NonSupportedCommandFactory extends CommandFactory{
    @Override
    protected Command createCommand() {
        return new NonSupportedCommand();
    }
}
