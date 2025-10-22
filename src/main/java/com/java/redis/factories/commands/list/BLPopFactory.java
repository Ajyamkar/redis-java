package com.java.redis.factories.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.list.BLPop;
import com.java.redis.factories.commands.CommandFactory;

public class BLPopFactory extends CommandFactory {
    @Override
    protected Command createCommand() {
        return new BLPop();
    }
}
