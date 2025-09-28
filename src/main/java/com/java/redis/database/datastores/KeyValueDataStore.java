package com.java.redis.database.datastores;

import com.java.redis.database.models.KeyExpiry;

import java.util.Optional;

public class KeyValueDataStore extends DataStoreGenericClass<String> {
    private final KeyExpiryDataStore keyExpiryDataStore = new KeyExpiryDataStore();

    public void storeKeyValueData(String key, String value, Optional<KeyExpiry> keyExpiry) {
        addNewData(key, value);
        keyExpiry.ifPresent(expiry -> keyExpiryDataStore.addNewData(key, expiry));
    }

    public String getKeyValueData(String key) throws RuntimeException {
        try {
            KeyExpiry keyExpiry = keyExpiryDataStore.getData(key);

            if (keyExpiry != null && keyExpiryDataStore.isKeyExpired(key, keyExpiry)) {
                keyExpiryDataStore.updateKeyExpiryDataStore(key, keyExpiry.getPreviousValue(), keyExpiry.getExpiresAt(), true);
                return null;
            }
            return getData(key);
        } catch (Exception e) {
            throw new RuntimeException("Exception while getting the value for key: " + key + " from DB is: " + e);
        }
    }
}
