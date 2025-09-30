package com.java.redis.factories.commands;

import com.java.redis.commands.Command;
import com.java.redis.commands.Echo;

public class EchoFactory extends CommandFactory {

    @Override
    protected Command createCommand() {
        return new Echo();
    }
}
