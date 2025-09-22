package com.java.redis.utils;

import static com.java.redis.utils.Constants.BULK_STRINGS_PREFIX;
import static com.java.redis.utils.Constants.END_OF_LINE;

public class SuccessResponseConstructor implements ResponseConstructor {
    @Override
    public byte[] construct(String message) {
        String response = (BULK_STRINGS_PREFIX + message.getBytes().length + END_OF_LINE + message + END_OF_LINE);
        System.out.println("response: "+response);
        return response.getBytes();
    }
}
