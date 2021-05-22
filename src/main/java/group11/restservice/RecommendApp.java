package group11.restservice;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class RecommendApp {

	public static void main(String[] args) throws JSONException, IOException {
		/*
		SpringApplication application = new SpringApplication(RecommendApp.class);
		application.run(args);
		*/
		SpringApplication.run(RecommendApp.class, args);
		//SpringApplication.run(milestone2.class, userData.getJavaInput());
	}
}
