package group11.restservice.controller;

import group11.restservice.model.RecoData;
import group11.restservice.repository.RecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private RecoRepository recoRepository;

    public HelloController(RecoRepository recoRepository) throws IOException {
        this.recoRepository = recoRepository;
    }

    @RequestMapping("/")
    public List<RecoData> helloWorld() {

        RecoData rd1 = new RecoData("1","Toy Story","Animation","http://www.imdb.com/title/tt0114709","https://m.media-amazon.com/images/M/MV5BMDU2ZWJlMjktMTRhMy00ZTA5LWEzNDgtYmNmZTEwZTViZWJkXkEyXkFqcGdeQXVyNDQ2OTk4MzI@..jpg","sampleplot");
        RecoData rd2 = new RecoData("1","Toy Story","Animation","http://www.imdb.com/title/tt0114709","https://m.media-amazon.com/images/M/MV5BMDU2ZWJlMjktMTRhMy00ZTA5LWEzNDgtYmNmZTEwZTViZWJkXkEyXkFqcGdeQXVyNDQ2OTk4MzI@..jpg","sampleplot");

        return Arrays.asList(rd1,rd2);
    }

}