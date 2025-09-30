package com.java.redis.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandOptions {
   public static List<String> SET_OPTIONS = List.of("EX", "PX", "EXAT", "PXAT", "NX", "XX","KEEPTTL","GET");

    public static int SET_DEFAULT_ARGS_SIZE = 2;
    public static int GET_DEFAULT_ARGS_SIZE = 1;
    public static int ECHO_DEFAULT_ARGS_SIZE = 1;
    public static int RPUSH_DEFAULT_ARGS_SIZE = 2;
    public static int LRANGE_DEFAULT_ARGS_SIZE = 3;
    public static int LPUSH_DEFAULT_ARGS_SIZE = 2;
    public static int LLEN_DEFAULT_ARGS_SIZE = 1;
    public static int LPOP_DEFAULT_ARGS_SIZE = 1;
}
