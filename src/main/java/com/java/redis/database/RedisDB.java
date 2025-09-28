package com.java.redis.database;

import com.java.redis.database.models.KeyExpiry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RedisDB {
    ConcurrentHashMap<String, String> keyValueDataStore = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, KeyExpiry> keyExpiryDataStore = new ConcurrentHashMap<>();

    ConcurrentHashMap<String, ArrayList<String>> keyListDataStore = new ConcurrentHashMap<>();

    public void storeKeyValueData(String key, String value, Optional<KeyExpiry> keyExpiry) {
        keyValueDataStore.put(key, value);
        keyExpiry.ifPresent(expiry -> keyExpiryDataStore.put(key, expiry));
    }

    public String getKeyValueData(String key) throws RuntimeException {
        try {
            KeyExpiry keyExpiry = keyExpiryDataStore.get(key);

            if (keyExpiry != null && isKeyExpired(key, keyExpiry)) {
                updateKeyExpiryDataStore(key, keyExpiry.getPreviousValue(), keyExpiry.getExpiresAt(), true);
                return null;
            }
            return keyValueDataStore.get(key);
        } catch (Exception e) {
            throw new RuntimeException("Exception while getting the value for key: " + key + " from DB is: " + e);
        }
    }

    private void updateKeyExpiryDataStore(String key, Optional<String> previousValue, Optional<Long> expiresAt, boolean isExpired) {
        KeyExpiry newExpiry = new KeyExpiry();
        newExpiry.setPreviousValue(previousValue);
        newExpiry.setExpiresAt(expiresAt);
        newExpiry.setIsExpired(isExpired);

        keyExpiryDataStore.put(key, newExpiry);
    }

    private boolean isKeyExpired(String key, KeyExpiry keyExpiry) {
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
        return keyValueDataStore;
    }

    public void printFullKeyExpiryDataStore() {
        for (Map.Entry<String, KeyExpiry> entry : keyExpiryDataStore.entrySet()) {
            KeyExpiry value = entry.getValue();
            System.out.println("key: " + entry.getKey() + " value.getPreviousValue: " + value.getPreviousValue() + " value.getIsExpired " + value.getIsExpired() + " value.getExpiresAt " + value.getExpiresAt());
        }
    }

    public ArrayList<String> getKeyList(String key) throws RuntimeException {
        return keyListDataStore.get(key);
    }

    public int storeKeyList(String key, List<String> valueInList) throws Exception {
        // Check if the key doesn't hold string value in the Db.
        if (getKeyValueData(key) == null) {
            try {
                // If the key is present in keyList store such append the new values
                ArrayList<String> ans = new ArrayList<>(getKeyList(key));
                ans.addAll(valueInList);
                keyListDataStore.put(key, ans);
                return ans.size();
            } catch (NullPointerException e) {
                // If the key is not present in keyList Store.
                keyListDataStore.put(key, new ArrayList<>(valueInList));
                return valueInList.size();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
        return -1;
    }
}
