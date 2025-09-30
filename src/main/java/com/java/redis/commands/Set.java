package com.java.redis.commands;

import com.java.redis.database.RedisDB;
import com.java.redis.models.ClientRequest;
import com.java.redis.database.models.KeyExpiry;
import com.java.redis.utils.ResponseConstructor;

import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

public class Set extends Command {

    @Override
    public void validateCommand(List<String> args) throws Exception {
        if (args.size() < CommandOptions.SET_DEFAULT_ARGS_SIZE) {
            throw new Exception("Redis SET command requires 2 arg but found " + args.size() + " args");
//            Optional<String> inValidOption = inValidCommandOption(CommandOptions.SET_OPTIONS, clientRequest);
//            if(inValidOption.isPresent()){
//                System.out.println("Unsupported Redis SET command option: "+inValidOption.get());
//                throw new Exception("Unsupported Redis SET command option: "+inValidOption.get());
//            }
        }
    }

//    private Optional<String> inValidCommandOption(List<String> setOptions, ClientRequest clientRequest) {
//        List<String> options = clientRequest.getCommandOptionKeyValue().keySet().stream().toList();
//
//        for (String option: options) {
//            if (!setOptions.contains(option.toUpperCase())) {
//                return Optional.of(option);
//            }
//        }
//        return Optional.empty();
//    }

    @Override
    public void executeCommand(OutputStream outputStream, ClientRequest clientRequest, RedisDB redisDB) throws Exception {
        try {
            List<String> args = clientRequest.getArgs();

            for (int i=2;i<args.size();i+=2){
                clientRequest.setCommandOptionKeyValue(args.get(i), args.get(i+1));
            }

            validateCommand(args);

            Optional<KeyExpiry> options = Optional.empty();
            if (args.size() > CommandOptions.SET_DEFAULT_ARGS_SIZE) {
                // TODO:Add support for options other than expiry options
                // TODO: Find Better ways to parse command option i.e use CommandOptionKeyValue
                options = constructExpiryOptionFields(args.getFirst(), args.get(2), args.get(3), redisDB);
            }

            redisDB.getKeyValueDataStore().storeKeyValueData(args.getFirst(), args.get(1), options);
            outputStream.write(ResponseConstructor.constructSimpleString("OK"));
            outputStream.flush();
        } catch (Exception e) {
            System.out.println("e: "+e);
            throw new RuntimeException("I/O error while replying to client", e);
        }
    }

    private Optional<KeyExpiry> constructExpiryOptionFields(String key, String optionalKey, String optionalValue, RedisDB redisDB) {
        long value = Long.parseLong(optionalValue);
        if (optionalKey.equalsIgnoreCase("EX")) {
            value = value * 1000; // 1s = 1000ms
        }
        KeyExpiry keyExpiry = new KeyExpiry();

        try {
            keyExpiry.setPreviousValue(Optional.of(redisDB.getKeyValueDataStore().getKeyValueData(key)));
        } catch (Exception e) {
            // key not found in DB.
            keyExpiry.setPreviousValue(Optional.empty());
        }

        keyExpiry.setExpiresAt(Optional.of(System.currentTimeMillis() + value));
        keyExpiry.setIsExpired(false);

        return Optional.of(keyExpiry);
    }
}
