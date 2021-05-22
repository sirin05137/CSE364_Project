package group11.restservice.controller;

import group11.Classified_by_vote;
import group11.milestone2;
import group11.restservice.model.UserData;
import group11.restservice.propertyeditor.UserDataEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



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
        //UserData ud = objectMapper.readValue(userdata, UserData.class);
        UserData ud = new UserData();
        ud.setGender(gender);
        ud.setAge(age);
        ud.setOccupation(occupation);
        ud.setGenre(genre);

        //String udjson = objectMapper.writeValueAsString(ud);
        //System.out.println(udjson);

        //milestone2.main(ud.getJavaInput());
        milestone2 program = new milestone2();

        //milestone2.get_classified_table();
        program.main(ud.getJavaInput());
        //ArrayList<Classified_by_vote> table = program.get_classified_table();

        //Arrays.toString(ud.getJavaInput())

        //return ud.getGender() + "/" + ud.getAge() + "/" + ud.getOccupation() + "/" + ud.getGenre();
        //return Arrays.toString(ud.getJavaInput());
        return program.get_classified_table();
    }


}
