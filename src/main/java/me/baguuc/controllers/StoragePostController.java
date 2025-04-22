package me.baguuc.controllers;

import me.baguuc.Main;
import me.baguuc.models.Item;
import me.baguuc.models.Storage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoragePostController {
    @PostMapping(value = "/storages")
    public String createStorage(@RequestBody Body body) {
        Storage newStorage = new Storage(body.capacity, body.maxTotalWeight);
        Main.STORAGE_MANAGER.addStorage(body.storageName, newStorage);

        return "";
    }

    record Body(
        String storageName,
        int capacity,
        float maxTotalWeight
    ) {}
}
