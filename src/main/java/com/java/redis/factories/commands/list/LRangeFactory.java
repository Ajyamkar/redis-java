package com.java.redis.factories.commands.list;

import com.java.redis.commands.Command;
import com.java.redis.commands.list.LRANGE;
import com.java.redis.factories.commands.CommandFactory;

public class LRangeFactory extends CommandFactory {
    @Override
    protected Command createCommand() {
        return new LRANGE();
    }
}
