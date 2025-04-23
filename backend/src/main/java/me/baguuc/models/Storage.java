package me.baguuc.models;

import com.google.gson.Gson;
import me.baguuc.Main;
import me.baguuc.errors.ExceptionCaseUnfulfilledException;
import me.baguuc.errors.MaxCapacityReachedException;
import me.baguuc.errors.MaxWeightReachedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {
    public List<Item> items;
    public int capacity;
    public int currentItemCount;
    public float maxTotalWeight;
    public float currentTotalWeight;

    public Storage(int capacity, float maxTotalWeight) {
        this.capacity = capacity;
        this.maxTotalWeight = maxTotalWeight;

        this.items = new ArrayList<>();
        this.currentItemCount = 0;
        this.currentTotalWeight = 0;
    }

    public HashMap<String, Object> getSerialized() {
        HashMap<String, Object> serialized = new HashMap<>();
        serialized.put("items", this.items.stream().map(Item::getSerialized).toList());
        serialized.put("capacity", this.capacity);
        serialized.put("currentItemCount", this.currentItemCount);
        serialized.put("maxTotalWeight", this.maxTotalWeight);
        serialized.put("currentTotalWeight", this.currentTotalWeight);

        return serialized;
    }

    public boolean addItem(Item item) throws MaxCapacityReachedException, MaxWeightReachedException, ExceptionCaseUnfulfilledException {
        if(this.currentItemCount == capacity) {
            throw new MaxCapacityReachedException();
        }

        if(this.currentTotalWeight + item.weightKg > this.maxTotalWeight) {
            throw new MaxWeightReachedException();
        }

        if(item.isSensitive && item.weirdnessLevel == 7) {
            if(this.capacity / 2 <= this.currentItemCount) {
                throw new ExceptionCaseUnfulfilledException();
            }
        }

        this.items.add(item);
        this.currentItemCount++;
        this.currentTotalWeight += item.weightKg;

        return true;
    }

    public void removeItem(String name) {
        int i;

        for(i = 0; i < this.items.size(); i++) {
            if(this.items.get(i).name.equals(name)) {
                this.items.remove(i);
                break;
            }
        }
    }

    public List<Item> getSensitiveOrHeavy(float minWeight) {
        return this.items
            .stream()
            .filter(item -> item.isSensitive || item.weightKg > minWeight)
            .toList();
    }

    public float getMeanWeirdnessLevel() {
        int n = 0;
        int totalWeirdness = 0;

        for(Item item : this.items) {
            n++;
            totalWeirdness += item.weirdnessLevel;
        }

        // bez tego może wystąpić błąd dzielenia przez zero
        return n == 0 ? 0 : (float) totalWeirdness / n;
    }

    public List<HashMap<String, Object>> getAllItemDescriptions() {
        return this.items
            .stream()
            .map(Item::getSerialized)
            .toList();
    }
}
