package group11;

import javax.swing.text.html.HTMLDocument;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class milestone2 {
    public static void main(String[] args) throws IOException {


        ArrayList<HashMap<String,String>> movie_rating_matrix = new ArrayList<>();
        BufferedReader get_movie_data = new BufferedReader(new FileReader("data/movies.dat"));
        while(true) {
            HashMap<String, String> inner_map = new HashMap<>();
            String line = get_movie_data.readLine();
            if (line == null)
                break;
            //System.out.println(line);
            String[] word = line.split("::");
            inner_map.put("movieID", word[0]);
            inner_map.put("Title", word[1]);
            inner_map.put("Genre", word[2]);
            inner_map.put("total_rating", "0");
            inner_map.put("counter", "0");
            movie_rating_matrix.add(inner_map);
        }
        get_movie_data.close();
        BufferedReader get_link_data = new BufferedReader(new FileReader("data/links.dat"));
        while(true){
            String line = get_link_data.readLine();
            if (line == null)
                break;
            String[] word = line.split("::");
            for(int i=0;i<movie_rating_matrix.size();i++){
                if(movie_rating_matrix.get(i).get("movieID").equals(word[0])){
                    movie_rating_matrix.get(i).put("link", "http://www.imdb.com/title/tt"+word[1]);
                    break;
                }
            }
        }
        get_link_data.close();
        BufferedReader get_rating_data = new BufferedReader(new FileReader("data/ratings.dat"));
        while(true){
            String line = get_rating_data.readLine();
            if (line == null)
                break;
            String[] word = line.split("::");
            for(int i=0;i<movie_rating_matrix.size();i++){
                if(movie_rating_matrix.get(i).get("movieID").equals(word[1])){
                    double a = Double.parseDouble(movie_rating_matrix.get(i).get("total_rating"))+Double.parseDouble(word[2]);
                    movie_rating_matrix.get(i).put("total_rating",Double.toString(a));
                    double b = Double.parseDouble(movie_rating_matrix.get(i).get("counter"))+1;
                    movie_rating_matrix.get(i).put("counter",Double.toString(b));
                    break;
                }
            }
        }
        get_rating_data.close();
        //System.out.print(movie_rating_matrix);
    }
}
