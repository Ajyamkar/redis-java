package com.java.redis.database.datastores;

import java.util.ArrayList;
import java.util.List;

public class KeyListDataStore extends DataStoreGenericClass<ArrayList<String>> {
    private final KeyValueDataStore keyValueDataStore;

    public KeyListDataStore(KeyValueDataStore keyValueDataStore){
        this.keyValueDataStore = keyValueDataStore;
    }

    public int storeKeyList(String key, List<String> valueInList) throws Exception {
        // Check if the key doesn't hold string value in the Db.
        if (keyValueDataStore.getKeyValueData(key) == null) {
            try {
                // If the key is present in keyList store such append the new values
                ArrayList<String> ans = new ArrayList<>(getData(key));
                ans.addAll(valueInList);
                updateData(key, ans);
                return ans.size();
            } catch (NullPointerException e) {
                // If the key is not present in keyList Store.
                addNewData(key, new ArrayList<>(valueInList));
                return valueInList.size();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
        return -1;
    }
}
