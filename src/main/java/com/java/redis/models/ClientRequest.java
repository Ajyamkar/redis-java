package com.java.redis.models;

import com.java.redis.commands.SupportedCommand;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ClientRequest {
    private int requestLength;
    private SupportedCommand command;
    private String nonSupportedCommandName;
    private final ConcurrentHashMap<String, String> commandOptionKeyValue =  new ConcurrentHashMap<>();
    private List<String> args;

    public int getRequestLength() {
        return requestLength;
    }

    public void setRequestLength(int requestLength) {
        this.requestLength = requestLength;
    }

    public SupportedCommand getCommand() {
        return command;
    }

    public void setCommand(String command) throws RuntimeException {
        try {
            this.command = SupportedCommand.valueOf(command);
        } catch (IllegalArgumentException e) {
            setNonSupportedCommandName(command);
            throw new RuntimeException("Unsupported Redis command: "+ command);
        }
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public String getNonSupportedCommandName() {
        return nonSupportedCommandName;
    }

    public void setNonSupportedCommandName(String nonSupportedCommandName) {
        this.nonSupportedCommandName = nonSupportedCommandName;
    }

    public ConcurrentHashMap<String, String> getCommandOptionKeyValue() {
        return commandOptionKeyValue;
    }

    public void setCommandOptionKeyValue(String option, String value) {
       commandOptionKeyValue.put(option, value);
    }
}
