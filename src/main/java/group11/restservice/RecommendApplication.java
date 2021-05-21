package group11.restservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;


@SpringBootApplication
public class RecommendApplication {

	public static void main(String[] args) throws JSONException, IOException {
		/*
		SpringApplication application = new SpringApplication(RecommendApplication.class);
		application.run(args);
		*/
		SpringApplication.run(RecommendApplication.class, args);
		//SpringApplication.run(milestone2.class, userData.getJavaInput());
	}
}

@RestController
class WebController {
	/*
	private final UserData userdata;

	WebController(UserData userdata) {
		this.userdata = userdata;
	}
	*/

	// Spring handler method that accepts POST requests
	@PostMapping(path = "/users", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public UserData addUserData (@RequestBody UserData userdata) {

		//System.out.println("User Repository after POST : ");
		//System.out.println(Arrays.toString(userdata.getJavaInput()) );
		return userdata;
	}

	// localhost:8080/users/recommend/get
	// Controller to handle GET requests
	// @RequestParam annotation supports only simple data types such as int and String <-> @RequestBody
	@GetMapping(path = "/users/recommend/get",consumes = "application/json", produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public UserData getUserRecommendation(@RequestParam String userdata) throws JsonMappingException, JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		UserData userData = mapper.readValue(userdata, UserData.class);

		//String udjson = objectMapper.writeValueAsString(userData);
		//System.out.println(udjson);

		//System.out.println(
		//"UserData --> " + userData.getGender() + " , " + userData.getAge() + " , " + userData.getOccupation() + " , " + userData.getGenre()
		//Arrays.toString(userData.getJavaInput()) );

		//milestone2.main(userData.getJavaInput());

		//public String getUserTest () {
		//}
		return userData;
	}
}


/*
// Bind the JSON parameter to an object of JsonFormat class
// localhost:8080/users/recommend/get2
@GetMapping("/users/recommend/get2")
@ResponseStatus(value = HttpStatus.OK)
@ResponseBody
public UserData get2UserRecommendation(@RequestParam UserData userdata) {
	// custom logic
	return userdata;
}
*/



/*
//WebDataBinder is used to populate form fields onto beans.
//When form fields are read on server side, it is better to read them as per their corresponding types than as strings.
@InitBinder
public void initBinder(WebDataBinder binder) {
	binder.registerCustomEditor(UserData.class, new JsonFormatEditor(objectMapper));
}
 */
	/*
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
	*/







