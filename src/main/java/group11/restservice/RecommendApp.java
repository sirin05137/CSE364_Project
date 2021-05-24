package group11.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RecommendApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		try {
			SpringApplication.run(RecommendApp.class, args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	// Used when deploying to a standalone servlet container ????
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RecommendApp.class);
	}
}
