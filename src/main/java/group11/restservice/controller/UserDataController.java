package group11.restservice.controller;

import group11.milestone2;
import group11.restservice.exception.InputInvalidException;
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
    // localhost:8080/users/recommendations/get
    //	@GetMapping(path = "/users/recommendations/get",consumes = "application/json", produces = "application/json")
    @GetMapping("/recommendations/get")
    @ResponseStatus(value = HttpStatus.OK)
    public String getUserRecommendations(@RequestBody(required = false) String userdata) throws JsonMappingException, JsonProcessingException, IOException, InputInvalidException {

        // Set UserData input from json input
        UserData ud = objectMapper.readValue(userdata, UserData.class);

        // Check Validity of UserData
        boolean isException = false;
        ArrayList<String> msg = new ArrayList<>();

        if (!milestone2.check_gender_validity(ud.getGender())) {
            isException = true;
            msg.add("Entered gender input ("+ ud.getGender() +") is invalid.");
            //throw new InputInvalidException("gender", ud.getGender());
        }
        if (!milestone2.check_age_validity(ud.getAge())) {
            isException = true;
            msg.add("Entered age input ("+ ud.getAge() +") is invalid.");
        }
        if (!milestone2.check_occu_validity(ud.getOccupation())) {
            isException = true;
            msg.add("Entered occupation input ("+ ud.getOccupation() +") is invalid.");
        }
        if (!ud.getGenre().equals("")) {
            if (!milestone2.check_genre_validity(ud.getGenre())) {
                isException = true;
                msg.add("Entered genre input ("+ ud.getGenre() +") is invalid.");
            }
        }

        if (isException) {
            throw new InputInvalidException(msg);
        }



        // Execute milestone2.class with UserData input
        milestone2 program = new milestone2();
        program.main(ud.getJavaInput());

        // Make json arraylist (Recommendations) from classified table
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

    @GetMapping("/recommendations/test")
    @ResponseStatus(value = HttpStatus.OK)
    public String get2UserRecommendations() throws JsonMappingException, JsonProcessingException, IOException {
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
