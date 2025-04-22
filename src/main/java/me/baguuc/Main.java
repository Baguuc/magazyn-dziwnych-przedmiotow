package me.baguuc;

import me.baguuc.models.StorageManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	public static final StorageManager STORAGE_MANAGER = new StorageManager();

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
