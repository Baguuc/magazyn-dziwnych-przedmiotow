package me.baguuc.controllers;

import me.baguuc.Main;
import me.baguuc.errors.*;
import me.baguuc.models.Item;
import me.baguuc.models.Storage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ItemPostController {
    @PostMapping(value = "/storages/{storageName}")
    public ResponseEntity createItem(@PathVariable("storageName") String storageName, @RequestBody Body body) {
        Item newItem = new Item(
            body.name,
            body.weightKg,
            body.weirdnessLevel,
            body.isSensitive
        );
        try {
            Main.STORAGE_MANAGER.addItem(storageName, newItem);
        } catch (StorageNotFoundException|MaxCapacityReachedException|MaxWeightReachedException|ExceptionCaseUnfulfilledException|InvalidWeirdnessLevelException e) {
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
