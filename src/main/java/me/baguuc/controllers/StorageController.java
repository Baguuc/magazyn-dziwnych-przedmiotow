package me.baguuc.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.baguuc.Main;
import me.baguuc.models.Item;
import me.baguuc.models.Storage;
import me.baguuc.models.StorageManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class StorageController {
    @GetMapping(value = "/storages", produces = "application/json")
    public String controller() {
        record Inline(
            String name,
            List<Item> items,
            int capacity,
            int currentItemCount,
            float maxTotalWeight,
            float currentTotalWeight
        ) {}

        List<Inline> storages = Main.STORAGE_MANAGER
            .getStorages()
            .stream()
            .map(entry -> {
                String name = entry.getKey();
                Storage storage = entry.getValue();

                return new Inline(
                    name,
                    storage.items,
                    storage.capacity,
                    storage.currentItemCount,
                    storage.maxTotalWeight,
                    storage.currentTotalWeight
                );
            })
            .toList();

        Gson mapper = new Gson();
        String json = mapper.toJson(storages);

        return json;
    }
}
