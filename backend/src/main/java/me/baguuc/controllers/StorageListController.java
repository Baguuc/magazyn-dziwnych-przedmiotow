package me.baguuc.controllers;

import com.google.gson.Gson;
import me.baguuc.Main;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class StorageListController {
    @GetMapping(value = "/storages", produces = "application/json")
    public String listStorages() {
        List<HashMap<String, Object>> serialized = Main.STORAGE_MANAGER.getAllSerialized();
        Gson mapper = new Gson();

        return mapper.toJson(serialized);
    }
}
