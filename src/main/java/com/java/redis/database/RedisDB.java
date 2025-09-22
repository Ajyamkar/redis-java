package com.java.redis.database;

import java.util.HashMap;

public class RedisDB {
    HashMap<String, String> data = new HashMap<>();

    public void store(String key, String value){
        data.put(key,value);
    }

    public String getData(String key){
        return data.get(key);
    }

    public HashMap<String, String> getFullData(){
        return data;
    }
}
