package me.baguuc.controllers;

import me.baguuc.Main;
import me.baguuc.errors.ExceptionCaseUnfulfilledException;
import me.baguuc.errors.MaxCapacityReachedException;
import me.baguuc.errors.MaxWeightReachedException;
import me.baguuc.errors.StorageNotFoundException;
import me.baguuc.models.Item;
import me.baguuc.models.Storage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemPostController {
    @PostMapping(value = "/storages/{storageName}")
    public ResponseEntity createStorage(@PathVariable("storageName") String storageName, @RequestBody Body body) {
        Item newItem = new Item(
            body.name,
            body.weightKg,
            body.weirdnessLevel,
            body.isSensitive
        );
        try {
            Main.STORAGE_MANAGER.addItem(storageName, newItem);
        } catch (StorageNotFoundException|MaxCapacityReachedException|MaxWeightReachedException|ExceptionCaseUnfulfilledException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.toString());
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }

    record Body(
        String name,
        float weightKg,
        int weirdnessLevel,
        boolean isSensitive
    ) {}
}
