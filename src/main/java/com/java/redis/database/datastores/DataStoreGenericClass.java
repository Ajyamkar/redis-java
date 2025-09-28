package com.java.redis.database.datastores;

import java.util.concurrent.ConcurrentHashMap;

public abstract class DataStoreGenericClass<T> {
    private final ConcurrentHashMap<String, T> dataStore = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, T> getDataStore(){
        return dataStore;
    }
    
    public void addNewData(String key, T value) {
        dataStore.put(key, value);
    }
    
    public void removeData(String key) {
        dataStore.remove(key);
    }
    
    public void updateData(String key, T value) {
        if(dataStore.containsKey(key)){
            dataStore.put(key, value);
        }
    }

    public T getData(String key) {
        return dataStore.get(key);
    }
}
