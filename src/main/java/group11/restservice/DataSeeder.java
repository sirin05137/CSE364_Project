package group11.restservice;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import group11.restservice.model.RecoData;
import group11.restservice.repository.RecoRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner{

    // lets reference our repository
    private RecoRepository recoRepository;

    // Creates a constructor of the DataSeeder
    public DataSeeder(RecoRepository recoRepository) {
        this.recoRepository = recoRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // delete all resorts if already exist
        this.recoRepository.deleteAll();

        // add movies to database
        Reader reader = Files.newBufferedReader(Paths.get("data/movies_corrected.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        final List<CSVRecord> records = csvParser.getRecords();
        for (CSVRecord csvRecord : records) {
            // Accessing Values by Column Index

            String movieid = csvRecord.get(0);
            String title = csvRecord.get(1);
            String genre = csvRecord.get(2);
            //System.out.println("Record No - " + csvRecord.getRecordNumber());
            System.out.print(movieid + " / " + title + " / " + genre + "\n");

            /*
            RecoData recoData = null;

            recoData.setMovieid(movieid);
            recoData.setTitle(title);
            recoData.setGenre(genre);
            recoData.setImdblink("SAMPLELINK");
            */

            //set img link
            this.recoRepository.save(new RecoData(movieid, title, genre, "SAMPLELINK"));

        }

        //RecoData rd1 = new RecoData("A","B","C","D");
        //RecoData rd2 = new RecoData("E","F","G","H");


        //List<RecoData> recodata = Arrays.asList(rd1,rd2);
        //this.recoRepository.saveAll(recodata);

    }

}