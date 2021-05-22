package group11.restservice.controller;

import group11.milestone2;
import group11.restservice.model.RecoData;
import group11.restservice.model.UserData;
import group11.restservice.propertyeditor.UserDataEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/users")
public class UserDataController {
    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(UserData.class, new UserDataEditor(objectMapper));
    }

    // Spring handler method that accepts POST requests
    // localhost:8080/users/create
    //@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    @PostMapping("/create")
    public UserData addUserData (@RequestBody(required = false) UserData userdata) {

        return userdata;
    }


    // Controller to handle GET requests
    // localhost:8080/users/recommend/get
    //	@GetMapping(path = "/users/recommend/get",consumes = "application/json", produces = "application/json")
    @GetMapping("/recommend/get")
    @ResponseStatus(value = HttpStatus.OK)
    public String getUserRecommendation(@RequestParam(name = "gender", required = false) String gender,
                                        @RequestParam(name = "age", required = false) String age,
                                        @RequestParam(name = "occupation", required = false) String occupation,
                                        @RequestParam(name = "genre", required = false) String genre) throws JsonMappingException, JsonProcessingException, IOException {
        // Set UserData input from json input
        UserData ud = new UserData();
        //UserData ud = objectMapper.readValue(userdata, UserData.class);
        ud.setGender(gender);
        ud.setAge(age);
        ud.setOccupation(occupation);
        ud.setGenre(genre);

        // Execute milestone2.class with UserData input
        milestone2 program = new milestone2();
        program.main(ud.getJavaInput());

        // Make json arraylist (Recommendation) from classified table
        List<RecoData> recoList = new ArrayList<RecoData>();
        RecoData reco = null;

        int limit = 10; // number of movies to print out (Top 10 movies)

        for (int index = 0 ; index < limit ; index++){
            reco = new RecoData();
            reco = objectMapper.readValue(program.get_classified_table(index), RecoData.class);
            recoList.add(reco);
        }

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(recoList);
    }

    /*
    @GetMapping("/recommend/get2")
    @ResponseStatus(value = HttpStatus.OK)
    public UserData get2UserRecommendation(@RequestParam UserData userdata) throws JsonMappingException, JsonProcessingException, IOException {

        return userdata;
    }
     */

    @GetMapping("/recommend/test")
    @ResponseStatus(value = HttpStatus.OK)
    public String get2UserRecommendation() throws JsonMappingException, JsonProcessingException, IOException {
        RecoData reco = new RecoData();
        reco.setTitle("Toy Story (1995)");
        reco.setGenre("Animation");
        reco.setImdb("https://www.imdb.com/title/tt0114709/");

        //UserData ud = objectMapper.readValue(userdata, UserData.class);
        //MyValue value = mapper.readValue(new File("data.json"), MyValue.class);
        //value = mapper.readValue("{\"name\":\"Bob\", \"age\":13}", MyValue.class);

        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(reco);

        return json;
    }


}
