package me.baguuc.models;

import com.google.gson.Gson;
import me.baguuc.Main;
import me.baguuc.errors.ExceptionCaseUnfulfilledException;
import me.baguuc.errors.MaxCapacityReachedException;
import me.baguuc.errors.MaxWeightReachedException;
import me.baguuc.errors.StorageNotFoundException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class StorageManager extends HashMap<String, Storage> {
    public void addStorage(String name, Storage storage) {
        this.put(name, storage);
    }

    public HashMap<String, Object> getSerialized(String storageName) throws StorageNotFoundException {
        if(!storageExists(storageName)) {
            throw new StorageNotFoundException();
        }

        HashMap<String, Object> serialized = this.get(storageName).getSerialized();
        serialized.put("name", storageName);

        return serialized;
    }

    public List<HashMap<String, Object>> getAllSerialized() {
        return this.keySet()
            .stream()
            .map(key -> {
                try {
                    HashMap<String, Object> serialized = this.getSerialized(key);
                    serialized.put("name", key);

                    return serialized;
                } catch (StorageNotFoundException e) {
                    // nigdy sie nie stanie, poprostu kompiler nie rozumie
                }

                return null;
            })
            .toList();
    }

    public void addItem(String storageName, Item item) throws StorageNotFoundException, MaxCapacityReachedException, MaxWeightReachedException, ExceptionCaseUnfulfilledException {
        if(!storageExists(storageName)) {
            throw new StorageNotFoundException();
        }

        this.get(storageName).addItem(item);
    }

    public void removeItem(String storageName, String itemName) throws StorageNotFoundException {
        if(!storageExists(storageName)) {
            throw new StorageNotFoundException();
        }

        this.get(storageName).removeItem(itemName);
    }

    public List<Item> getSensitiveOrHeavy(String storageName, float minWeight) throws StorageNotFoundException {
        if(!storageExists(storageName)) {
            throw new StorageNotFoundException();
        }

        return this.get(storageName).getSensitiveOrHeavy(minWeight);
    }

    public float getMeanWeirdnessLevel(String storageName) throws StorageNotFoundException {
        if(!storageExists(storageName)) {
            throw new StorageNotFoundException();
        }

        return this.get(storageName).getMeanWeirdnessLevel();
    }

    public void getAllDescriptions(String storageName, float minWeight) throws StorageNotFoundException {
        if(!storageExists(storageName)) {
            throw new StorageNotFoundException();
        }

        this.get(storageName).getAllItemDescriptions();
    }

    private boolean storageExists(String name) {
        return this.containsKey(name);
    }
}
