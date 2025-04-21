package me.baguuc.models;

import me.baguuc.errors.MaxCapacityReachedException;
import me.baguuc.errors.MaxWeightReachedException;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Item> items;
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

    public void addItem(Item item) throws MaxCapacityReachedException, MaxWeightReachedException {
        if(this.currentItemCount == capacity) {
            throw new MaxCapacityReachedException();
        }

        if(this.currentTotalWeight + item.weightKg > this.maxTotalWeight) {
            throw new MaxWeightReachedException();
        }

        this.items.add(item);
        this.currentItemCount++;
        this.currentTotalWeight += item.weightKg;
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

    public void printSensitiveOrHeavy(float minWeight) {
        for(Item item : this.items) {
            if(item.isSensitive || item.weightKg > minWeight) {
                System.out.println(item.description());
            }
        }
    }

    public void printAll() {
        for(Item item : this.items) {
            System.out.println(item.description());
        }
    }
}
