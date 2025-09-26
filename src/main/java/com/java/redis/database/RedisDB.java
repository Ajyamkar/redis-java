package com.java.redis.database;

import com.java.redis.models.KeyExpiry;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class RedisDB {
    ConcurrentHashMap<String, String> dataStore = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, KeyExpiry> keyExpiryDataStore = new ConcurrentHashMap<>();

    public void store(String key, String value, Optional<KeyExpiry> keyExpiry) {
        dataStore.put(key, value);
        keyExpiry.ifPresent(expiry -> keyExpiryDataStore.put(key, expiry));
    }

    public String getData(String key) {
        KeyExpiry keyExpiry = keyExpiryDataStore.get(key);

        if (keyExpiry != null && isKeyExpired(key, keyExpiry)) {
            updateKeyExpiryDataStore(key, keyExpiry.getPreviousValue(), keyExpiry.getExpiresAt(), true);
            return null;
        }
        return dataStore.get(key);
    }

    public void updateKeyExpiryDataStore(String key, Optional<String> previousValue, Optional<Long> expiresAt, boolean isExpired) {
        KeyExpiry newExpiry = new KeyExpiry();
        newExpiry.setPreviousValue(previousValue);
        newExpiry.setExpiresAt(expiresAt);
        newExpiry.setIsExpired(isExpired);

        keyExpiryDataStore.put(key, newExpiry);
    }

    public boolean isKeyExpired(String key, KeyExpiry keyExpiry) {
        if (keyExpiryDataStore.containsKey(key)) {

            if (keyExpiry.getIsExpired()) {
                return true;
            }

            if (keyExpiry.getExpiresAt().isPresent()) {
                return (Long) System.currentTimeMillis() - keyExpiry.getExpiresAt().get() > 0;
            }
        }
        return true;
    }

    public ConcurrentHashMap<String, String> getFullData() {
        return dataStore;
    }

    public ConcurrentHashMap<String, KeyExpiry> getFullKeyExpiryDataStore() {
        return keyExpiryDataStore;
    }

    public void printFullKeyExpiryDataStore() {
        for (Map.Entry<String, KeyExpiry> entry : keyExpiryDataStore.entrySet()) {
            KeyExpiry value = entry.getValue();
            System.out.println("key: " + entry.getKey() + " value.getPreviousValue: " + value.getPreviousValue() + " value.getIsExpired " + value.getIsExpired() + " value.getExpiresAt " + value.getExpiresAt());

        }
    }
}
