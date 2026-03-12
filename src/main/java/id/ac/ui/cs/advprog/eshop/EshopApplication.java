package id.ac.ui.cs.advprog.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EshopApplication {

	/**
	 * Protected constructor to prevent instantiation of this utility class.
	 * PMD requires a comment inside empty constructors.
	 */
	protected EshopApplication() {
		// This constructor is intentionally empty.
	}

	public static void main(String[] args) {
		SpringApplication.run(EshopApplication.class, args);
	}
}