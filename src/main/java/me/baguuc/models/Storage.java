package me.baguuc.models;

import me.baguuc.errors.MaxCapacityReachedException;
import me.baguuc.errors.MaxWeightReachedException;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Item> items;
    private int capacity;
    private int currentItemCount;
    private float maxTotalWeight;
    private float currentTotalWeight;

    public Storage(int capacity, int maxTotalWeight) {
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
        this.currentTotalWeight += 1;
    }

    public void printAll() {
        for(Item item : this.items) {
            System.out.println(item.description());
        }
    }
}
