package me.baguuc.controllers;

import com.google.gson.Gson;
import me.baguuc.Main;
import me.baguuc.errors.StorageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ItemDeleteController {
    @DeleteMapping(value = "/storages/{storageName}/{itemName}", produces = "application/json")
    public ResponseEntity deleteItem(@PathVariable("storageName") String storageName, @PathVariable("itemName") String itemName) {
        try {
            Main.STORAGE_MANAGER.removeItem(storageName, itemName);
        } catch (StorageNotFoundException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.toString());
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }
}
