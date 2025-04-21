package me.baguuc;

import me.baguuc.errors.*;
import me.baguuc.models.*;
import me.baguuc.util.Input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static StorageManager storageManager = new StorageManager();

    public static void main(String[] args) throws StorageNotFoundException {
        System.out.println("Witaj w magazynie dziwnych przedmiotów");

        while(true) {
            // czyszczenie konsoli (komenda clear w unix lub cls w dos)
            System.out.print("\033[H\033[2J");
            System.out.flush();

            printStorageCommands();
            System.out.println();
            printStorages();

            String input = Input.inputString("$ ");

            System.out.println();
            try {
                execute(input);
            } catch (CurrentStorageUninitializedException ex) {
                System.out.println("Nie wybrano jeszcze żadnego magazynu.");
            } catch(StorageNotFoundException ex) {
                System.out.println("Nie znaleziono magazynu.");
            } catch (MaxCapacityReachedException ex) {
                System.out.println("Nie można dodać: wypełniono już cały magazyn.");
            } catch (MaxWeightReachedException ex) {
                System.out.println("Nie można dodać: magazyn nie może utrzymać już więcej wagi.");
            } catch (ExceptionCaseUnfulfilledException ex) {
                System.out.println("Nie można dodać: Zbyt ryzykowny przedmiot (delikatny i dziwny na poziomie 7) przy obecnym zapełnieniu.");
            }
            System.out.println();
        }
    }

    private static void printStorages() {
        System.out.println("Masz aktualnie " + storageManager.getStorageCount() + " magazynów.");

        if(storageManager.getStorageCount() > 0) {
            System.out.println();
            System.out.println("Twoje magazyny:");

            for(Map.Entry<String, Storage> entry : storageManager.getStorages()) {
                String name = entry.getKey();
                Storage storage = entry.getValue();

                System.out.println(name + ":");
                System.out.println("  zapełnienie: " + storage.currentItemCount + "/" + storage.capacity);
                System.out.println("  przechowywana waga: " + storage.currentTotalWeight + "/" + storage.maxTotalWeight);
            }
            System.out.println();
        }
    }

    private static void printStorageCommands() {
        System.out.println("Oto komendy magazynu:");
        System.out.println("stworz - tworzy magazyn");
        System.out.println("wybierz - wybiera magazyn");
        System.out.println("dodaj - dodaje przedmiot do wybranego magazynu");
        System.out.println("usun - usuwa pierwszy napotkany przedmiot o podanej nazwie z wybranego magazynu");
        System.out.println("wypisz_wszystkie - wypisuje wszystkie przedmioty z wybranego magazynu");
        System.out.println("wypisz_delikatne_lub_ciezkie - wypisuje wszystkie przedmioty które są delikatne lub cięższe niż podany próg wagowy z wybranego magazynu");
        System.out.println("wypisz_srednia_dziwnosc - oblicza i wypisuje średni poziom dziwności przedmiotów z wybranego magazynu");
    }

    private static void execute(String s)
    throws
        MaxCapacityReachedException,
        MaxWeightReachedException,
        CurrentStorageUninitializedException,
        StorageNotFoundException,
        ExceptionCaseUnfulfilledException
    {
        switch(s) {
            case "stworz": create(); break;
            case "wybierz": select(); break;
            case "dodaj": add(); break;
            case "usun": delete(); break;
            case "wypisz_wszystkie": printAll(); break;
            case "wypisz_delikatne_lub_ciezkie": printSensitiveOrHeavy(); break;
            case "wypisz_srednia_dziwnosc": printMeanWeirdnessLevel(); break;
            default: return;
        }
    }

    private static void create() {
        String name = Input.inputString("Podaj nazwe magazynu (bedzie używana jako identyfikator w poleceniu \"wybierz\"): ");
        int capacity = Input.inputInteger("Podaj pojemność magazynu: ");
        float maxTotalWeight = Input.inputFloat("Podaj maksymalną wagę w kilogramach którą magazyn może przetrzymać: ");

        storageManager.addStorage(name, new Storage(capacity, maxTotalWeight));
    }

    private static void select() throws StorageNotFoundException {
        String name = Input.inputString("Podaj nazwe magazynu który chcesz wybrać: ");

        storageManager.setCurrentStorage(name);
    }

    private static void add() throws CurrentStorageUninitializedException, MaxCapacityReachedException, MaxWeightReachedException, ExceptionCaseUnfulfilledException {
        String name = Input.inputString("Podaj nazwe: ");
        float weight = Input.inputFloat("Podaj wage: ");
        int level = Input.inputInteger("Podaj poziom dziwności: ");
        boolean isSensitive = Input.inputBoolean("Podaj czy przedmiot jest delikatny (true/false): ");

        Item item = new Item(name, weight, level, isSensitive);
        storageManager.getCurrentStorage().addItem(item);
    }

    private static void delete() throws CurrentStorageUninitializedException {
        String name = Input.inputString("Podaj nazwe przedmiotu do usunięcia: ");

        storageManager.getCurrentStorage().removeItem(name);
    }

    private static void printAll() throws CurrentStorageUninitializedException {
        storageManager.getCurrentStorage().printAll();
    }

    private static void printSensitiveOrHeavy() throws CurrentStorageUninitializedException {
        float minWeight = Input.inputFloat("Podaj próg wagowy (minimalną wagę): ");

        storageManager.getCurrentStorage().printSensitiveOrHeavy(minWeight);
    }

    private static void printMeanWeirdnessLevel() throws CurrentStorageUninitializedException {
        System.out.println(storageManager.getCurrentStorage().calculateMeanWeirdnessLevel());
    }
}