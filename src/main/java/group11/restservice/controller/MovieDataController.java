package group11.restservice.controller;

import group11.milestone3;
import group11.restservice.exception.InputInvalidException;
import group11.restservice.model.RecoData;
import group11.restservice.model.MovieData;
import group11.restservice.propertyeditor.MovieDataEditor;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import group11.restservice.repository.RecoRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Service
@RestController
@RequestMapping("/movies")
public class MovieDataController {
    private ObjectMapper objectMapper;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private RecoRepository recoRepository;

    public MovieDataController() throws IOException {
        if (recoRepository != null) {recoRepository.deleteAll();}


        Reader reader = Files.newBufferedReader(Paths.get("data/movies_corrected.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        final List<CSVRecord> records = csvParser.getRecords();
        for (CSVRecord csvRecord : records) {
            // Accessing Values by Column Index
            RecoData recoData = new RecoData();

            String movieid = csvRecord.get(0);
            String title = csvRecord.get(1);
            String genre = csvRecord.get(2);
            //System.out.println("Record No - " + csvRecord.getRecordNumber());
            System.out.print(movieid + " / " + title + " / " + genre + "\n");

            recoData.setMovieid(movieid);
            recoData.setTitle(title);
            recoData.setGenre(genre);
            recoData.setImdblink("SAMPLELINK");
            //set img link

            recoRepository.save(recoData);
        }
        LOG.info("MongoDB setup done");


    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(MovieData.class, new MovieDataEditor(objectMapper));
    }

    // This has to return all the movies
    //@RequestMapping(value = "", method = RequestMethod.GET)

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public List<RecoData> getAllMovies() {
        LOG.info("Getting all movies.");
        //SAMPLE DATA (WILL BE DELETED)
        RecoData recoData = null;
        assert false;
        recoData.setMovieid("hi");
        recoData.setTitle("thisis");
        recoData.setGenre("sample");
        recoData.setImdblink("linkhere");

        //("hi", "thisis", "sample", "imdblinkhere");
        this.recoRepository.save(recoData);

        return this.recoRepository.findAll();
    }



    @GetMapping("/recommendations")
    @ResponseStatus(value = HttpStatus.OK)
    public String getMovieRecommendations(@RequestParam(required = false) String moviedata) throws Exception {

        // Set UserData input from json input
        MovieData md = objectMapper.readValue(moviedata, MovieData.class);
        System.out.println(Arrays.toString(md.getJavaInput()));

        // Check Validity of Moviedata ( throws InputInvalidException when invalid)
        boolean isException = false;
        ArrayList<String> msg = new ArrayList<>();

        if (!milestone3.check_title_validity(md.getTitle(), milestone3.make_movie_data_map())) {
            isException = true;
            msg.add("Entered title input (" + md.getTitle() + ") is invalid.");
        }
        if (!milestone3.check_limit_validity(md.getLimit())) {
            isException = true;
            msg.add("Entered limit input (" + md.getLimit() + ") is invalid.");
        }

        if (isException) {
            throw new InputInvalidException(msg);
        }


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
