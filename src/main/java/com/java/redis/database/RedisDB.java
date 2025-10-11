package com.java.redis.database;

import com.java.redis.database.datastores.KeyListDataStore;
import com.java.redis.database.datastores.KeyValueDataStore;

public enum RedisDB {
    INSTANCE;

    private final KeyValueDataStore keyValueDataStore;
    private final KeyListDataStore keyListDataStore;

    RedisDB() {
        this.keyValueDataStore = new KeyValueDataStore();
        this.keyListDataStore = new KeyListDataStore(keyValueDataStore);
    }

    public KeyListDataStore getKeyListDataStore() {
        return keyListDataStore;
    }

    public KeyValueDataStore getKeyValueDataStore() {
        return keyValueDataStore;
    }
}
