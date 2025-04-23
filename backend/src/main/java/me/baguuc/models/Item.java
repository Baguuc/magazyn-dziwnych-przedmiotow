package me.baguuc.models;

import com.google.gson.Gson;

import java.util.HashMap;

public class Item {
    public String name;
    public float weightKg;
    public int weirdnessLevel;
    public boolean isSensitive;

    public Item(String name, float weightKg, int weirdnessLevel, boolean isSensitive) {
        this.name = name;
        this.weightKg = weightKg;
        this.weirdnessLevel = weirdnessLevel;
        this.isSensitive = isSensitive;
    }

    public String description() {
        HashMap<String, Object> serialized = this.getSerialized();
        Gson mapper = new Gson();

        return mapper.toJson(serialized);
    }

    public HashMap<String, Object> getSerialized() {
        HashMap<String, Object> serialized = new HashMap<>();
        serialized.put("name", name);
        serialized.put("weightKg", weightKg);
        serialized.put("weirdnessLevel", weirdnessLevel);
        serialized.put("isSensitive", isSensitive ? "TAK" : "NIE");

        return serialized;
    }
}
