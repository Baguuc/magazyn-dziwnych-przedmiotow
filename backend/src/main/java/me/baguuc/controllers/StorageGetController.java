package me.baguuc.controllers;

import com.google.gson.Gson;
import me.baguuc.Main;
import me.baguuc.errors.StorageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
public class StorageGetController {
    @GetMapping(value = "/storages/{storageName}", produces = "application/json")
    public ResponseEntity listStorages(@PathVariable("storageName") String storageName) {
        HashMap<String, Object> serialized = null;

        try {
            serialized = Main.STORAGE_MANAGER.getSerialized(storageName);
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
