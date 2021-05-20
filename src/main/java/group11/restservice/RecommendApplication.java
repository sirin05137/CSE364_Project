package group11.restservice;

import group11.milestone2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecommendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommendApplication.class, args);
		//SpringApplication.run(milestone2.main().class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

	@RestController
	@RequestMapping("/users/recommend/")
	public class getRecommend {

		@GetMapping("/get")
		public RecommendResponse getRecommendTest () {
			RecommendResponse recommendResponse = new RecommendResponse("F", "25", "Grad Student", "Action|War");
			return recommendResponse;
		}

	}

}

/*
public class JsonFormat {
	// default constructor + getters + setters
	private String gender;
	private String age;
	private String occupation;
	private String genre;
}

@PostMapping("/create")
@ResponseBody
public JsonFormat createJsonFormat (@RequestBody JsonFormat jsonformat) {
	// custom logic
	return jsonformat;
}

@GetMapping("/get")
@ResponseBody
public JsonFormat getJsonFormat(@RequestParam String jsonformat) throws JsonMappingException, JsonProcessingException {
	JsonFormat jf = objectMapper.readValue(jsonformat, JsonFormat.class);
	return jf;
}

// Bind the JSON parameter to an object of JsonFormat class
@GetMapping("/get2")
@ResponseBody
public JsonFormat get2JsonFormat(@RequestParam JsonFormat jsonformat) {
	// custom logic
	return jsonformat;
}

@InitBinder
public void initBinder(WebDataBinder binder) {
	binder.registerCustomEditor(JsonFormat.class, new JsonFormatEditor(objectMapper));
}
*/

