package com.java.redis.utils;

import java.util.List;

import static com.java.redis.utils.Constants.*;

public class ResponseConstructor {

    public static byte[] constructSimpleString(String message) {
        return (SIMPLE_STRING_PREFIX + message + END_OF_LINE).getBytes();
    }

    public static byte[] constructBulkString(String message) {
        String response = (BULK_STRINGS_PREFIX + message.getBytes().length + END_OF_LINE + message + END_OF_LINE);
        return response.getBytes();
    }

    public static byte[] nullBulkString() {
        return (BULK_STRINGS_PREFIX + SIMPLE_ERROR_PREFIX + "1" + END_OF_LINE).getBytes();
    }

    public static byte[] constructErrorResponse(String message) {
        return (ERROR_PREFIX + message + END_OF_LINE).getBytes();
    }

    public static byte[] constructIntegerReply(Integer value) {
        return (SIMPLE_INTEGER_PREFIX + value + END_OF_LINE).getBytes();
    }

    public static byte[] constructArrayResponse(List<String> values) {
        String start = ARRAY_PREFIX + values.size() + END_OF_LINE;
        StringBuilder end = new StringBuilder();
        for(String val:values){
            end.append(BULK_STRINGS_PREFIX).append(val.length()).append(END_OF_LINE).append(val).append(END_OF_LINE);
        }

        return (start + end.toString()).getBytes();
    }
}
