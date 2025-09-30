package com.java.redis.factories.commands;

import com.java.redis.commands.Command;
import com.java.redis.commands.Set;

public class SetFactory extends CommandFactory {
    @Override
    protected Command createCommand() {
        return new Set();
    }
}
