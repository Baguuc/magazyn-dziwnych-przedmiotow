package me.baguuc.models;

import me.baguuc.errors.ExceptionCaseUnfulfilledException;
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

    public void printSensitiveOrHeavy(float minWeight) {
        for(Item item : this.items) {
            if(item.isSensitive || item.weightKg > minWeight) {
                System.out.println(item.description());
            }
        }
    }

    public float calculateMeanWeirdnessLevel() {
        int n = 0;
        int totalWeirdness = 0;

        for(Item item : this.items) {
            n++;
            totalWeirdness += item.weirdnessLevel;
        }

        // bez tego może wystąpić błąd dzielenia przez zero
        return n == 0 ? 0 : (float) totalWeirdness / n;
    }

    public void printAll() {
        for(Item item : this.items) {
            System.out.println(item.description());
        }
    }
}
