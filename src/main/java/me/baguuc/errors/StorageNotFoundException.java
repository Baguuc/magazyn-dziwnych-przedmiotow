package me.baguuc.errors;

public class StorageNotFoundException extends Throwable {
    public String toString() {
        return "STORAGE_NOT_FOUND";
    }
}