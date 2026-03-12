package id.ac.ui.cs.advprog.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EshopApplication {

	/**
	 * Private constructor to prevent instantiation of this utility class.
	 */
	private EshopApplication() {
		// This constructor is intentionally empty.
	}

	public static void main(String[] args) {
		SpringApplication.run(EshopApplication.class, args);
	}
}