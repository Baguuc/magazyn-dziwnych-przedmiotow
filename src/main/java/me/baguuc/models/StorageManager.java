package me.baguuc.models;

import me.baguuc.errors.CurrentStorageUninitializedException;
import me.baguuc.errors.StorageNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class StorageManager {
    public Map<String, Storage> storages;
    public String currentStorageName;

    public StorageManager() {
        this.storages = new HashMap<>();
    }

    public int getStorageCount() {
        return this.storages.keySet().size();
    }

    public void addStorage(String name, Storage storage) {
        this.storages.put(name, storage);
    }

    public Storage getCurrentStorage() throws CurrentStorageUninitializedException {
        if(this.currentStorageName == null) {
            throw new CurrentStorageUninitializedException();
        }

        // jedyna metoda która pozwala na zmienianie "currentStorage" nie pozwala zmienić go na klucz
        // który nie istnieje, więc nie musimy już tego sprawdzać
        return this.storages.get(this.currentStorageName);
    }

    public void setCurrentStorage(String name) throws StorageNotFoundException {
        if(!this.storages.containsKey(name)) {
            throw new StorageNotFoundException();
        }

        this.currentStorageName = name;
    }
}
