package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;

import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

public abstract class Command {
    public OutputStream outputStream;
    public ClientRequest clientRequest;
    public RedisDB redisDB;

    public void validateCommand(SupportedCommand command) throws Exception {
        List<String> args = this.clientRequest.getArgs();

        if (command == SupportedCommand.LPUSH && args.size() < CommandOptions.LPUSH_DEFAULT_ARGS_SIZE){
            throw new Exception("Redis LPUSH command requires minimum 2 args but found " + args.size() + " args");
        }

        if (command == SupportedCommand.LRANGE && args.size() < CommandOptions.LRANGE_DEFAULT_ARGS_SIZE){
            throw new Exception("Redis LRANGE command requires minimum 3 args but found " + args.size() + " args");
        }

        if (command == SupportedCommand.RPUSH && args.size() < CommandOptions.RPUSH_DEFAULT_ARGS_SIZE){
            throw new Exception("Redis RPUSH command requires minimum 2 args but found " + args.size() + " args");
        }

        if (command == SupportedCommand.SET && args.size() != CommandOptions.SET_DEFAULT_ARGS_SIZE) {
            Optional<String> inValidOption = inValidCommandOption(CommandOptions.SET_OPTIONS);
            if(inValidOption.isPresent()){
                System.out.println("Unsupported Redis SET command option: "+inValidOption.get());
                throw new Exception("Unsupported Redis SET command option: "+inValidOption.get());
            }
        }

        if (command == SupportedCommand.GET && args.size() != CommandOptions.GET_DEFAULT_ARGS_SIZE) {
            throw new Exception("Redis GET command requires 1 arg but found " + args.size() + " args");
        }

        if (command == SupportedCommand.ECHO && args.size() != CommandOptions.ECHO_DEFAULT_ARGS_SIZE) {
            throw new Exception("Redis ECHO command requires 1 arg but found " + args.size() + " args");
        }

        if (command == SupportedCommand.PING && !args.isEmpty()) {
            throw new Exception("Redis PING command requires 0 arg but found " + args.size() + " args");
        }
    }

    private Optional<String> inValidCommandOption(List<String> setOptions) {
        List<String> options = this.clientRequest.getCommandOptionKeyValue().keySet().stream().toList();

        for (String option: options) {
            if (!setOptions.contains(option.toUpperCase())) {
                return Optional.of(option);
            }
        }
        return Optional.empty();
    }

    public void executeCommand() throws Exception {
    }
}
