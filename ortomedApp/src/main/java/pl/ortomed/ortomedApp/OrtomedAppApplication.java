package pl.ortomed.ortomedApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrtomedAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrtomedAppApplication.class, args);
	}

}
