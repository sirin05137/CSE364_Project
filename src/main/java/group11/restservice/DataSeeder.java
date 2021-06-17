package group11.restservice;

import java.io.BufferedReader;
import java.io.FileReader;
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

        //System.out.print("\nIMG link missing for : ");
        final List<CSVRecord> records = csvParser.getRecords();
        for (CSVRecord csvRecord : records) {
            // Accessing Values by Column Index
            String movieid = csvRecord.get(0);
            String title = csvRecord.get(1);
            String genre = csvRecord.get(2);
            String imdblink = "NO_IMDB_LINK_FOUND"; //if not replaced, it means imdb link is missing
            String imglink = "NO_IMG_LINK_FOUND"; //if not replaced, it means img link is missing

            //GET IMDB link FROM CSV FILE
            BufferedReader imdblink_reader = new BufferedReader(new FileReader("data/links.csv"));
            while(true){
                String line = imdblink_reader.readLine();
                if (line == null)
                    break;
                String[] word = line.split(",");

                if(movieid.equals(word[0])){
                    imdblink = "http://www.imdb.com/title/tt" + word[1];
                    break;
                }
            }
            imdblink_reader.close();

            //GET IMG link FROM CSV FILE
            BufferedReader imglink_reader = new BufferedReader(new FileReader("data/movie_poster.csv"));
            while(true){
                String line = imglink_reader.readLine();
                if (line == null)
                    break;
                String[] word = line.split(",");

                if(movieid.equals(word[0])){
                    imglink = word[1];
                    break;
                }
            }
            imglink_reader.close();

            // Save to the repository
            //System.out.print(movieid + " / " + title + " / " + genre +  " / " + imdblink + " / " + imglink + "\n");
            //if (imglink.equals("NO_IMG_LINK_FOUND")) { System.out.print(movieid+","); }
            this.recoRepository.save(new RecoData(movieid, title, genre, imdblink, imglink));
        }
        //System.out.print("\n");

        //RecoData rd1 = new RecoData("A","B","C","D");
        //RecoData rd2 = new RecoData("E","F","G","H");

        //List<RecoData> recodata = Arrays.asList(rd1,rd2);
        //this.recoRepository.saveAll(recodata);
    }

}