package me.baguuc.models;

import com.google.gson.Gson;

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
        record Inline(
            String name,
            float weightKg,
            int weirdnessLevel,
            String isSensitive
        ) {}

        Inline toSerialize = new Inline(
            name,
            weightKg,
            weirdnessLevel,
            isSensitive ? "TAK" : "NIE"
        );
        Gson mapper = new Gson();

        return mapper.toJson(toSerialize);
    }
}
