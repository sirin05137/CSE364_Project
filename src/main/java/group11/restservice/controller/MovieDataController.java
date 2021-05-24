package group11.restservice.controller;

import group11.milestone3;
import group11.restservice.model.RecoData;
import group11.restservice.model.MovieData;
import group11.restservice.propertyeditor.MovieDataEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieDataController {
    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(MovieData.class, new MovieDataEditor(objectMapper));
    }

    @PostMapping("/create")
    public MovieData addMovieData (@RequestBody(required = false) MovieData moviedata) {
        return moviedata;
    }

    @GetMapping("/recommendations")
    @ResponseStatus(value = HttpStatus.OK)
    public String getMovieRecommendations(@RequestBody(required = false) String moviedata) throws Exception {

        // Set UserData input from json input
        MovieData md = objectMapper.readValue(moviedata, MovieData.class);
        System.out.println(Arrays.toString(md.getJavaInput()));
        // Check Validity of Moviedata ( throws InputInvalidException when invalid)
        /* code */

        // Execute milestone3.class with MovieData input
        milestone3 program = new milestone3();
        program.main(md.getJavaInput());

        // Make json arraylist (Recommendations) from classified table
        List<RecoData> recoList = new ArrayList<RecoData>();
        RecoData reco = null;

        int limit = Integer.parseInt(md.getLimit());


        for (int index = 0 ; index < limit ; index++){
            reco = new RecoData();
            reco = objectMapper.readValue(program.get_classified_table(index), RecoData.class);
            recoList.add(reco);
        }



        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(recoList);
        //return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(md);
    }

}
