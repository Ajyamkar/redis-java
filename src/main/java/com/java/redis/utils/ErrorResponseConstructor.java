package com.java.redis.utils;

import static com.java.redis.utils.Constants.END_OF_LINE;
import static com.java.redis.utils.Constants.ERROR_PREFIX;

public class ErrorResponseConstructor implements ResponseConstructor {

    @Override
    public byte[] construct(String message) {
        return (ERROR_PREFIX + message + END_OF_LINE).getBytes();
    }
}
