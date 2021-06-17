package group11.restservice;

import group11.restservice.controller.MovieDataController;
import group11.restservice.model.RecoData;
import group11.restservice.repository.RecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RecommendApp extends SpringBootServletInitializer {

	@Autowired
	RecoRepository recoRepository;
	//MovieDataController movieDataController;

	public static void main(String[] args) {
		try {
			SpringApplication.run(RecommendApp.class, args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	/*
	public void run() throws Exception {
		recoRepository.save(new RecoData("a","a","a","a"));
		for (RecoData recoData : recoRepository.findAll()) {
			System.out.println(recoData);
		}
	}

	 */
	/*
	// Used when deploying to a standalone servlet container ????
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RecommendApp.class);
	}
	 */
}
