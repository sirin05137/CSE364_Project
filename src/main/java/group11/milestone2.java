package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

class Movie_data_node{
    String movieID;
    String title;
    String genre;
    //String link;
    int total_rating=0;
    int counter=0;
    //double W=0;

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setGenre(String genre) { this.genre = genre; }
    /*public void setLink(String link) {
        this.link = link;
    }*/
    public void setTotal_rating(int total_rating) {
        this.total_rating += total_rating;
    }
    public void setCounter() {
        this.counter++;
    }
    /*public void setW(double CC, int mm){
        if(counter==0){
            this.W=CC;
        }
        else {
            BigDecimal tr = new BigDecimal(String.valueOf(total_rating));
            BigDecimal v = new BigDecimal(String.valueOf(counter));
            BigDecimal R = tr.divide(v, 2, RoundingMode.HALF_UP);
            BigDecimal C = new BigDecimal(String.valueOf(CC));
            BigDecimal m = new BigDecimal(String.valueOf(mm));
            BigDecimal upper = v.multiply(R).add(m.multiply(C));
            BigDecimal lower = v.add(m);
            this.W = upper.divide(lower, 2, RoundingMode.HALF_UP).doubleValue();
        }
    }*/
    public String getMovieID() {
        return movieID;
    }
    public String getTitle() {
        return title;
    }
    public String getGenre() {
        return genre;
    }
   /*public String getLink() {
        return link;
    }*/
    public int getTotal_rating() {
        return total_rating;
    }
    public int getCounter() {
        return counter;
    }
    //public double getW() { return W; }
    public void print_node(){
        /*System.out.print("{movieID : "+this.movieID+", Title : "+this.title+", Genre : "+this.genre+", link : "
                +this.link+", total_rating : "+this.total_rating+", counter : "+this.counter+"}");*/
        System.out.println("{movieID : "+this.movieID+", Title : "+this.title+", Genre : "+this.genre+", total_rating : "
                           +this.total_rating+", counter : "+this.counter+"}");
    }
}
class Classified_by_vote extends Movie_data_node implements Comparable{
    double W=0;
    String link;

    public int compareTo(Object o){
        return compareTo((Classified_by_vote) o);
    }
    public int compareTo(Classified_by_vote another){
        double thisVal = this.getW();
        double anotherVal = another.getW();
        return (thisVal<anotherVal ? 1 : (thisVal == anotherVal ? 0 : -1));
    }

    public Classified_by_vote(Movie_data_node movie_rating_data){
        this.movieID = movie_rating_data.getMovieID();
        this.title = movie_rating_data.getTitle();
        this.genre = movie_rating_data.getGenre();
        this.total_rating = movie_rating_data.getTotal_rating();
        this.counter = movie_rating_data.getCounter();
    }

    public void setLink(String link) {
        this.link = link;
    }
    public void setW(double CC, int mm){
        if(counter==0){
            this.W=CC;
        }
        else {
            BigDecimal tr = new BigDecimal(String.valueOf(total_rating));
            BigDecimal v = new BigDecimal(String.valueOf(counter));
            BigDecimal R = tr.divide(v, 3, RoundingMode.HALF_UP);
            BigDecimal C = new BigDecimal(String.valueOf(CC));
            BigDecimal m = new BigDecimal(String.valueOf(mm));
            BigDecimal upper = v.multiply(R).add(m.multiply(C));
            BigDecimal lower = v.add(m);
            this.W = upper.divide(lower, 3, RoundingMode.HALF_UP).doubleValue();
        }
    }
    public String getLink() {
        return link;
    }
    public double getW() {
         return W;
     }
     public void print_node(){
        System.out.println("{movieID : "+this.movieID+", Title : "+this.title+", Genre : "+this.genre+", link : "
                +this.link+", total_rating : "+this.total_rating+", counter : "+this.counter+", W : "+this.W+"}");
     }
}

public class milestone2 {

    static void make_table(ArrayList<Movie_data_node> movie_rating_matrix) throws IOException{
        BufferedReader get_movie_data = new BufferedReader(new FileReader("data/movies.dat"));
        while(true){
            Movie_data_node inner_node = new Movie_data_node();
            String line = get_movie_data.readLine();
            if (line == null)
                break;
            //System.out.println(line);
            String[] word = line.split("::");
            inner_node.setMovieID(word[0]);
            inner_node.setTitle(word[1]);
            inner_node.setGenre(word[2].toLowerCase());
            movie_rating_matrix.add(inner_node);
        }
        get_movie_data.close();
        /*BufferedReader get_link_data = new BufferedReader(new FileReader("data/links.dat"));
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
        get_link_data.close();*/
        BufferedReader get_rating_data = new BufferedReader(new FileReader("data/ratings.dat"));
        while(true) {
            String line = get_rating_data.readLine();
            if (line == null)
                break;
            String[] word = line.split("::");
            for (int i = 0; i < movie_rating_matrix.size(); i++) {
                if (movie_rating_matrix.get(i).getMovieID().equals(word[1])) {
                    movie_rating_matrix.get(i).setTotal_rating(Integer.parseInt(word[2]));
                    movie_rating_matrix.get(i).setCounter();
                    break;
                }
            }
        }
        get_rating_data.close();

    }
    static double total_average_rating(ArrayList<Movie_data_node> movie_rating_matrix){
        BigDecimal sum_of_average_rating = new BigDecimal("0.0");
        int count = 0;
        for(int i=0;i< movie_rating_matrix.size();i++){
            if(movie_rating_matrix.get(i).getCounter()!=0){
                BigDecimal a = new BigDecimal(String.valueOf(movie_rating_matrix.get(i).getTotal_rating()));
                BigDecimal b = new BigDecimal(String.valueOf(movie_rating_matrix.get(i).getCounter()));
                sum_of_average_rating=sum_of_average_rating.add(a.divide(b,3,RoundingMode.HALF_UP));
                count++;
            }
        }
        //System.out.println(count);
        BigDecimal c = new BigDecimal(String.valueOf(count));
        if(count==0){ //movie_rating_matrix에 원소가 없거나 투표수가 0인 영화만 있는 경우
            System.out.println("no count");
            System.exit(1);
        }
        //System.out.println(sum_of_average_rating.doubleValue());
        //System.out.println(c.doubleValue());
        BigDecimal result = sum_of_average_rating.divide(c,2,RoundingMode.HALF_UP);
        return result.doubleValue();
    }
    static int Percentile(ArrayList<Movie_data_node> movie_rating_matrix, double p){
        ArrayList<Integer> vote_counting_list = new ArrayList<Integer>();
        for(int i=0;i<movie_rating_matrix.size();i++) {
            if (movie_rating_matrix.get(i).getCounter() != 0) {
                vote_counting_list.add(movie_rating_matrix.get(i).getCounter());
            }
        }
        if(vote_counting_list.size()==0){
            System.out.println("No counter!");
            System.exit(1);
        }
        Collections.sort(vote_counting_list); //오름차순으로 정렬
        BigDecimal One = new BigDecimal("1.0");
        BigDecimal ratio = new BigDecimal(String.valueOf(p));
        BigDecimal num = new BigDecimal(String.valueOf(vote_counting_list.size()));
        BigDecimal np = ratio.multiply(num);
        if(np.remainder(One).doubleValue()==0.0){
            //System.out.println("test");
            int a = np.intValue();
            return (vote_counting_list.get(a-1)+vote_counting_list.get(a))/2;
        }
        else{
            //System.out.println("elsecase");
            //System.out.println(vote_counting_list);
            int b=np.intValue();
            return vote_counting_list.get(b-1);
        }
    }
    static void make_classified_table(ArrayList<Classified_by_vote> classified_table, ArrayList<Movie_data_node> movie_rating_matrix, double C, int m) throws IOException{
        for(int i=0;i<movie_rating_matrix.size();i++){
            if(movie_rating_matrix.get(i).getCounter()>=m){
                Classified_by_vote inner_data = new Classified_by_vote(movie_rating_matrix.get(i));
                classified_table.add(inner_data);
            }
        }
        if(classified_table.size()==0){
            System.out.println("There are no movie which get more than "+m+" vote");
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
    static void print_output_format(ArrayList<Classified_by_vote> classified_table){
        for (int i = 0; i < 10; i++) {
            System.out.println(classified_table.get(i).getTitle() + " (" + classified_table.get(i).getLink() + ")");
        }
    }
    static void make_table_with_genre(ArrayList<Movie_data_node> movie_rating_matrix, ArrayList<Movie_data_node> matrix_genre_classified, String[] input_genre){
        for(int i=0; i<movie_rating_matrix.size();i++) {
            for (int j = 0; j < input_genre.length; j++) {
                if (movie_rating_matrix.get(i).getGenre().contains(input_genre[j])) {  //.contains에서 오류발생 가능->dbgenre 배열 만들기
                    matrix_genre_classified.add(movie_rating_matrix.get(i));
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

        //long start = System.currentTimeMillis();

        ArrayList<Movie_data_node> movie_rating_matrix = new ArrayList<>();
        make_table(movie_rating_matrix);

        if(args.length==3) {
            double C = total_average_rating(movie_rating_matrix);
            int m = Percentile(movie_rating_matrix, 0.95);
            //System.out.println(m);

            ArrayList<Classified_by_vote> classified_table = new ArrayList<>();
            make_classified_table(classified_table, movie_rating_matrix, C, m);
            print_output_format(classified_table);

            /*for(int i=0;i<classified_table.size();i++){
                classified_table.get(i).print_node();
            }*/
        }
        else if(args.length==4) {

            ArrayList<Movie_data_node> movie_rating_matrix_genre_classified = new ArrayList<>();
            String[] input_genre = args[3].toLowerCase().split("\\|");
            //String[] input_genre = {"adventure"};
            make_table_with_genre(movie_rating_matrix, movie_rating_matrix_genre_classified,input_genre);

            /*for(int i=0;i<movie_rating_matrix_genre_classified.size();i++){
                movie_rating_matrix_genre_classified.get(i).print_node();
            }*/
            double C = total_average_rating(movie_rating_matrix_genre_classified);
            int m = Percentile(movie_rating_matrix_genre_classified,0.8);
            ArrayList<Classified_by_vote> classified_table = new ArrayList<>();
            make_classified_table(classified_table, movie_rating_matrix_genre_classified, C, m);
            print_output_format(classified_table);

            /*for(int i=0;i<classified_table.size();i++){
                classified_table.get(i).print_node();
            }*/
        }
        else{
            System.out.println("Error");
            System.exit(1);
        }


        //long end = System.currentTimeMillis();
        //System.out.println(end-start  );
        /*for(int i=0;i<classified_table.size();i++){
            classified_table.get(i).print_node();
        }*/
    }
}