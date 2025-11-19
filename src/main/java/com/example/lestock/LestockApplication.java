package com.example.lestock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LestockApplication {

	public static void main(String[] args) {
		SpringApplication.run(LestockApplication.class, args);
	}

}
