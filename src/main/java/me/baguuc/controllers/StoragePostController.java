package me.baguuc.controllers;

import me.baguuc.Main;
import me.baguuc.models.Item;
import me.baguuc.models.Storage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoragePostController {
    @PostMapping(value = "/storages")
    public ResponseEntity createStorage(@RequestBody Body body) {
        Storage newStorage = new Storage(body.capacity, body.maxTotalWeight);
        if(body.name == "" || body.name == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
        }
        Main.STORAGE_MANAGER.addStorage(body.name, newStorage);

        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }

    record Body(
        String name,
        int capacity,
        float maxTotalWeight
    ) {}
}
