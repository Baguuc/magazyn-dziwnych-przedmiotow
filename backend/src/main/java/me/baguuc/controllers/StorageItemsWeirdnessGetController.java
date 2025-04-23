package me.baguuc.controllers;

import com.google.gson.Gson;
import me.baguuc.Main;
import me.baguuc.errors.StorageNotFoundException;
import me.baguuc.models.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
public class StorageItemsWeirdnessGetController {
    @GetMapping(value = "/storages/{storageName}/items/weirdness", produces = "application/json")
    public ResponseEntity getStorages(
        @PathVariable("storageName") String storageName
    ) {
        HashMap<String, Object> serialized = new HashMap<>();

        try {
            serialized.put("mean", Main.STORAGE_MANAGER.getMeanWeirdnessLevel(storageName));
        } catch (StorageNotFoundException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.toString());
        }
        Gson mapper = new Gson();

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(mapper.toJson(serialized));
    }
}
