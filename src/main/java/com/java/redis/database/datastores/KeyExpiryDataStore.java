package com.java.redis.database.datastores;

import com.java.redis.database.models.KeyExpiry;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class KeyExpiryDataStore extends DataStoreGenericClass<KeyExpiry> {

    public boolean isKeyExpired(String key, KeyExpiry keyExpiry) {
        if (getDataStore().containsKey(key)) {

            if (keyExpiry.getIsExpired()) {
                return true;
            }

            if (keyExpiry.getExpiresAt().isPresent()) {
                return (Long) System.currentTimeMillis() - keyExpiry.getExpiresAt().get() > 0;
            }
        }
        return true;
    }

    public void updateKeyExpiryDataStore(String key, Optional<String> previousValue, Optional<Long> expiresAt, boolean isExpired) {
        KeyExpiry newExpiry = new KeyExpiry();
        newExpiry.setPreviousValue(previousValue);
        newExpiry.setExpiresAt(expiresAt);
        newExpiry.setIsExpired(isExpired);

        updateData(key, newExpiry);
    }

}
