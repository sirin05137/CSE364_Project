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

        RecoData rd1 = new RecoData("1","Toy Story","Animation","http://www.imdb.com/title/tt0114709","https://m.media-amazon.com/images/M/MV5BMDU2ZWJlMjktMTRhMy00ZTA5LWEzNDgtYmNmZTEwZTViZWJkXkEyXkFqcGdeQXVyNDQ2OTk4MzI@..jpg");
        RecoData rd2 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");
        RecoData rd3 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");
        RecoData rd4 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");
        RecoData rd5 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");
        RecoData rd6 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");
        RecoData rd7 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");
        RecoData rd8 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");
        RecoData rd9 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");
        RecoData rd10 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");
        RecoData rd11 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");
        RecoData rd12 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");
        RecoData rd13 = new RecoData("2","Jumanji","Adventure","http://www.imdb.com/title/tt0113497","https://m.media-amazon.com/images/M/MV5BZTk2ZmUwYmEtNTcwZS00YmMyLWFkYjMtNTRmZDA3YWExMjc2XkEyXkFqcGdeQXVyMTQxNzMzNDI@..jpg");

        return Arrays.asList(rd1,rd2,rd3,rd4,rd5,rd6,rd7,rd8,rd9,rd10,rd11,rd12,rd13);
    }

}