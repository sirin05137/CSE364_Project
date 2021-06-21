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
        Reader reader = Files.newBufferedReader(Paths.get("/root/project/apache-tomcat-8.5.68/webapps/cse364-project/WEB-INF/classes/movies_corrected.csv"));
        //Reader reader = new BufferedReader(new FileReader("movies_corrected.csv"));
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
            BufferedReader imdblink_reader = new BufferedReader(new FileReader("/root/project/apache-tomcat-8.5.68/webapps/cse364-project/WEB-INF/classes/links.csv"));
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
            BufferedReader imglink_reader = new BufferedReader(new FileReader("/root/project/apache-tomcat-8.5.68/webapps/cse364-project/WEB-INF/classes/movie_poster.csv"));
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

            if (imglink.equals("NO_IMG_LINK_FOUND")){
                //imglink = "/poster_alphabet/";
                imglink = "./poster_alphabet/";
                if (title.toLowerCase().charAt(0)=='a'){imglink += "a.png";}
                if (title.toLowerCase().charAt(0)=='b'){imglink += "b.png";}
                if (title.toLowerCase().charAt(0)=='c'){imglink += "c.png";}
                if (title.toLowerCase().charAt(0)=='d'){imglink += "d.png";}
                if (title.toLowerCase().charAt(0)=='e'){imglink += "e.png";}
                if (title.toLowerCase().charAt(0)=='f'){imglink += "f.png";}
                if (title.toLowerCase().charAt(0)=='g'){imglink += "g.png";}
                if (title.toLowerCase().charAt(0)=='h'){imglink += "h.png";}
                if (title.toLowerCase().charAt(0)=='i'){imglink += "i.png";}
                if (title.toLowerCase().charAt(0)=='j'){imglink += "j.png";}
                if (title.toLowerCase().charAt(0)=='k'){imglink += "k.png";}
                if (title.toLowerCase().charAt(0)=='l'){imglink += "l.png";}
                if (title.toLowerCase().charAt(0)=='m'){imglink += "m.png";}
                if (title.toLowerCase().charAt(0)=='n'){imglink += "n.png";}
                if (title.toLowerCase().charAt(0)=='o'){imglink += "o.png";}
                if (title.toLowerCase().charAt(0)=='p'){imglink += "p.png";}
                if (title.toLowerCase().charAt(0)=='q'){imglink += "q.png";}
                if (title.toLowerCase().charAt(0)=='r'){imglink += "r.png";}
                if (title.toLowerCase().charAt(0)=='s'){imglink += "s.png";}
                if (title.toLowerCase().charAt(0)=='t'){imglink += "t.png";}
                if (title.toLowerCase().charAt(0)=='u'){imglink += "u.png";}
                if (title.toLowerCase().charAt(0)=='v'){imglink += "v.png";}
                if (title.toLowerCase().charAt(0)=='w'){imglink += "w.png";}
                if (title.toLowerCase().charAt(0)=='x'){imglink += "x.png";}
                if (title.toLowerCase().charAt(0)=='y'){imglink += "y.png";}
                if (title.toLowerCase().charAt(0)=='z'){imglink += "z.png";}
            }
            // Save to the repository
            //System.out.print(movieid + " / " + title + " / " + genre +  " / " + imdblink + " / " + imglink + "\n");
            //if (imglink.equals("NO_IMG_LINK_FOUND")) { System.out.print(movieid+","); }
            this.recoRepository.save( new RecoData(movieid, title, genre, imdblink, imglink) );
        }
        //System.out.print("\n");

        //RecoData rd1 = new RecoData("A","B","C","D");
        //RecoData rd2 = new RecoData("E","F","G","H");

        //List<RecoData> recodata = Arrays.asList(rd1,rd2);
        //this.recoRepository.saveAll(recodata);
    }

}