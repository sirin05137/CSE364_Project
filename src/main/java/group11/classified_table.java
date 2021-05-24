package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class classified_table {
    ArrayList<Classified_by_vote> classified_table = null;

    public classified_table() {
        classified_table = new ArrayList<>();
    }

    public static void make_classified_table(ArrayList<Movie_data_node> movie_rating_matrix, double C, int m) throws IOException {

        for(int i=0;i<movie_rating_matrix.size();i++){
            if(movie_rating_matrix.get(i).getCounter()>=m){
                Classified_by_vote inner_data = new Classified_by_vote(movie_rating_matrix.get(i));
                classified_table.add(inner_data);
            }
        }
        if(classified_table.size()==0){
            System.out.println("NoDBError : No movie available for more than "+m+" votes.");
            System.exit(1);
        }
        BufferedReader get_link_data = new BufferedReader(new FileReader("data/links.dat"));
        while(true){
            String line = get_link_data.readLine();
            if (line == null)
                break;
            String[] word = line.split("::");
            for(int i=0;i< classified_table.size();i++){
                if(classified_table.get(i).getMovieID().equals(word[0])){
                    classified_table.get(i).setLink("http://www.imdb.com/title/tt"+word[1]);
                    break;
                }
            }
        }
        get_link_data.close();
        for(int i=0;i<classified_table.size();i++){
            classified_table.get(i).setW(C,m);
        }
        Collections.sort(classified_table); //내림차순으로 정렬

    }


    public String get_classified_table(int index){
        // index : Rank of the movie (Top i-th movie)
        StringBuilder test = new StringBuilder();
        test.append("{"
                + "\"title\":\""
                + classified_table.get(index).getTitle()
                + "\","
                + "\"genre\":\""
                + classified_table.get(index).getGenre()
                + "\","
                + "\"imdb\":\""
                + classified_table.get(index).getLink()
                + "\""
                + "}");

        return test.toString();
    }
}
