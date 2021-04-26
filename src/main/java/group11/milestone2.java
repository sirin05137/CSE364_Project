package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class Noode{
    String movieID;
    String title;
    String genre;
    String link;
    double total_rating=0;
    double counter=0;

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setTotal_rating(double total_rating) {
        this.total_rating += total_rating;
    }
    public void setCounter() {
        this.counter++;
    }
    public String getMovieID() {
        return movieID;
    }
    public String getTitle() {
        return title;
    }
    public String getGenre() {
        return genre;
    }
    public String getLink() {
        return link;
    }
    public double getTotal_rating() {
        return total_rating;
    }
    public double getCounter() {
        return counter;
    }
    public void print_node(){
        System.out.print("{movieID : "+this.movieID+", Title : "+this.title+", Genre : "+this.genre+", link : "
                +this.link+", total_rating : "+this.total_rating+", counter : "+this.counter+"}");
    }
}

public class milestone2 {
    public static void main(String[] args) throws IOException {

        //long start = System.currentTimeMillis();

        ArrayList<Noode> movie_rating_matrix = new ArrayList<>();
        BufferedReader get_movie_data = new BufferedReader(new FileReader("data/movies.dat"));
        while(true){
            Noode inner_node = new Noode();
            String line = get_movie_data.readLine();
            if (line == null)
                break;
            //System.out.println(line);
            String[] word = line.split("::");
            inner_node.setMovieID(word[0]);
            inner_node.setTitle(word[1]);
            inner_node.setGenre(word[2]);
            movie_rating_matrix.add(inner_node);
        }
        get_movie_data.close();
        BufferedReader get_link_data = new BufferedReader(new FileReader("data/links.dat"));
        while(true){
            String line = get_link_data.readLine();
            if (line == null)
                break;
            String[] word = line.split("::");
            for(int i=0;i< movie_rating_matrix.size();i++){
                if(movie_rating_matrix.get(i).getMovieID().equals(word[0])){
                    movie_rating_matrix.get(i).setLink("http://www.imdb.com/title/tt"+word[1]);
                    break;
                }
            }
        }
        get_link_data.close();
        BufferedReader get_rating_data = new BufferedReader(new FileReader("data/ratings.dat"));
        while(true) {
            String line = get_rating_data.readLine();
            if (line == null)
                break;
            String[] word = line.split("::");
            for (int i = 0; i < movie_rating_matrix.size(); i++) {
                if (movie_rating_matrix.get(i).getMovieID().equals(word[1])) {
                    movie_rating_matrix.get(i).setTotal_rating(Double.parseDouble(word[2]));
                    movie_rating_matrix.get(i).setCounter();
                    break;
                }
            }
        }
        get_rating_data.close();





















        //long end = System.currentTimeMillis();
        //System.out.println(end-start);
        /*for (int i = 0; i < movie_data.size(); i++){
            movie_data.get(i).print_node();
        }*/
    }
}