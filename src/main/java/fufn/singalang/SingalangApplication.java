package fufn.singalang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SingalangApplication {

	public static void main(String[] args) {
		SpringApplication.run(SingalangApplication.class, args);
	}

}
