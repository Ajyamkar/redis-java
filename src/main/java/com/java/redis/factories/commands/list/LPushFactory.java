package com.java.redis.factories.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.list.LPush;
import com.java.redis.factories.commands.CommandFactory;

public class LPushFactory extends CommandFactory {
    @Override
    protected Command createCommand() {
        return new LPush();
    }
}
