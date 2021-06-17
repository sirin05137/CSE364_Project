package group11.restservice.controller;

import group11.milestone2;
import group11.restservice.exception.InputInvalidException;
import group11.restservice.model.RecoData;
import group11.restservice.model.UserData;
import group11.restservice.propertyeditor.UserDataEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import group11.restservice.repository.RecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserDataController {
    private ObjectMapper objectMapper;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private RecoRepository recoRepository;

    public UserDataController(RecoRepository recoRepository) throws IOException {
        this.recoRepository = recoRepository;

        LOG.info("MongoDB setup done");
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(UserData.class, new UserDataEditor(objectMapper));
    }

    // Controller to handle GET requests
    // localhost:8080/users/recommendations/get
    //	@GetMapping(path = "/users/recommendations/get",consumes = "application/json", produces = "application/json")
    @GetMapping("/recommendations")
    @ResponseStatus(value = HttpStatus.OK)
    public String getUserRecommendations(@RequestParam(name = "gender", required = false) String gender,
                                         @RequestParam(name = "age", required = false) String age,
                                         @RequestParam(name = "occupation", required = false) String occupation,
                                         @RequestParam(name = "genre", required = false) String genre) throws Exception {

        // Set UserData input from json input
        UserData ud = new UserData();
        if (gender != null) ud.setGender(gender);
        if (age != null) ud.setAge(age);
        if (occupation != null) ud.setOccupation(occupation);
        if (genre != null) ud.setGenre(genre);

        // Check Validity of UserData ( throws InputInvalidException when invalid)
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
        List<Optional<RecoData>> recoList = new ArrayList<>();

        int limit = 10; // number of movies to print out (Top 10 movies)

        for (int index = 0 ; index < limit ; index++){
            //System.out.println(this.recoRepository.findById(program.get_ith_movieid(index)));
            // above prints "Optional[group11.restservice.model.RecoData@3fb2331]"
            recoList.add(  this.recoRepository.findById( program.get_ith_movieid(index) )  );
        }

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(recoList);
    }


}
