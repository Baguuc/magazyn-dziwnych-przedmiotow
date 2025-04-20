package me.baguuc;

import me.baguuc.errors.*;
import me.baguuc.models.*;
import me.baguuc.util.Input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    private static StorageManager storageManager = new StorageManager();

    public static void main(String[] args) throws StorageNotFoundException {
        System.out.println("Witaj w magazynie dziwnych przedmiotów");

        if(storageManager.getStorageCount() == 0) {
            createStorage();
            storageManager.setCurrentStorage("master");
        }

        printStorageCommands();

        while(true) {
            String input = Input.inputString("$ ");
            execute(input);
        }
    }

    private static void createStorage() {
        System.out.println("Wygląda na to, że nie masz jeszcze magazynu. Stwórzmy go teraz:");
        int capacity = Input.inputInteger("Podaj pojemność magazynu: ");
        float maxTotalWeight = Input.inputFloat("Podaj maksymalną wagę w kilogramach którą magazyn może przetrzymać: ");

        storageManager.addStorage("master", new Storage(capacity, maxTotalWeight));
    }

    private static void printStorageCommands() {
        System.out.println("Oto komendy magazynu:");
        System.out.println("dodaj - dodaje przedmiot do magazynu");
    }

    private static void execute(String s) {
        switch(s) {
            case "dodaj": add(); break;
            default: return;
        }
    }

    private static void add() {
        String name = Input.inputString("Podaj nazwe: ");
        float weight = Input.inputFloat("Podaj wage: ");
        int level = Input.inputInteger("Podaj poziom dziwności: ");
        boolean isSensitive = Input.inputBoolean("Podaj czy przedmiot jest delikatny (true/false): ");

        try {
            Item item = new Item(name, weight, level, isSensitive);
            storageManager.getCurrentStorage().addItem(item);
        } catch (CurrentStorageUninitializedException ex) {
            System.out.println("Wystąbił błąd! Nie wybrano jeszcze żadnego magazynu.");
        } catch (MaxCapacityReachedException ex) {
            System.out.println("Nie można dodać: wypełniono cały magazyn.");
        } catch (MaxWeightReachedException ex) {
            System.out.println("Nie można dodać: magazyn nie może utrzymać już więcej wagi.");
        }
    }
}