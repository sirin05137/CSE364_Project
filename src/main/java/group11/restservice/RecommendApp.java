package group11.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecommendApp {

	public static void main(String[] args) {
		try {
			SpringApplication.run(RecommendApp.class, args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
