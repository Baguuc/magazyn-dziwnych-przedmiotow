package me.baguuc.controllers;

import com.google.gson.Gson;
import jakarta.websocket.server.PathParam;
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
public class StorageItemsHeavyGetController {
    @GetMapping(value = "/storages/{storageName}/items/heavy", produces = "application/json")
    public ResponseEntity getStorages(
        @PathVariable("storageName") String storageName,
        @RequestParam("weight") float weight
    ) {
        List<HashMap<String, Object>> serialized = null;

        try {
            serialized = Main.STORAGE_MANAGER.getSensitiveOrHeavy(storageName, weight)
                .stream()
                .map(Item::getSerialized)
                .toList();
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
