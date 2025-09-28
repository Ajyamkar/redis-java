package com.java.redis.database.models;

import java.util.Optional;

public class KeyExpiry {
    private Optional<String> previousValue;
    private Optional<Long> expiresAt;
    private Boolean isExpired;

    public Boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Optional<Long> getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Optional<Long> expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Optional<String> getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(Optional<String> previousValue) {
        this.previousValue = previousValue;
    }
}
