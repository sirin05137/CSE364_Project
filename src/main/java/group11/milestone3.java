package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class milestone3 {

    static int percentile(ArrayList<Movie_data_node> movie_rating_matrix, double p){
        ArrayList<Integer> vote_counting_list = new ArrayList<Integer>();
        for(int i=0;i<movie_rating_matrix.size();i++) {
            if (movie_rating_matrix.get(i).getCounter() != 0) {
                vote_counting_list.add(movie_rating_matrix.get(i).getCounter());
            }
        }
        if(vote_counting_list.size()==0){
            //System.out.println("No counter!");
            //System.exit(1);
            return 0;
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
    static double total_average_rating(ArrayList<Movie_data_node> movie_rating_matrix){
        BigDecimal sum_of_average_rating = new BigDecimal("0.0");
        int count = 0;
        for(int i=0;i< movie_rating_matrix.size();i++){
            if(movie_rating_matrix.get(i).getCounter()!=0){
                BigDecimal a = new BigDecimal(String.valueOf(movie_rating_matrix.get(i).getTotal_rating()));
                BigDecimal b = new BigDecimal(String.valueOf(movie_rating_matrix.get(i).getCounter()));
                sum_of_average_rating=sum_of_average_rating.add(a.divide(b,3, RoundingMode.HALF_UP));
                count++;
            }
        }
        //System.out.println(count);
        BigDecimal c = new BigDecimal(String.valueOf(count));
        if(count==0){ //movie_rating_matrix에 원소가 없거나 투표수가 0인 영화만 있는 경우
            System.out.println("NoDBError : There are no movie available for specified inputs.");
            System.exit(1);
        }
        //System.out.println(sum_of_average_rating.doubleValue());
        //System.out.println(c.doubleValue());
        BigDecimal result = sum_of_average_rating.divide(c,2,RoundingMode.HALF_UP);
        return result.doubleValue();
    }
    static ArrayList<Classified_by_vote> make_classified_table(ArrayList<Movie_data_node> movie_rating_matrix, double C, int m) throws IOException{
        ArrayList<Classified_by_vote> classified_table = new ArrayList<>();
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
        return classified_table;
    }
    static void print_output_format(ArrayList<Classified_by_vote> classified_table){
        for (int i = 0; i < 10; i++) {
            System.out.println(classified_table.get(i).getTitle() + "::" + classified_table.get(i).getGenre() + " (" + classified_table.get(i).getLink() + ")");
        }
    }

    public static void main(String[] args) throws IOException {


        //String A = "Vampire in Brooklyn (1995)";
        String id_of_input="";
        String genre_of_input="";
        HashMap<String, ArrayList<String>> movie_data_map = new HashMap<>();
        BufferedReader get_movie_data = new BufferedReader(new FileReader("data/movies.dat"));
        while(true) {
            ArrayList<String> inner_list = new ArrayList<>();
            String line = get_movie_data.readLine();
            if (line == null)
                break;
            //System.out.println(line);
            String[] word = line.split("::");
            inner_list.add(word[1]);
            inner_list.add(word[2]);
            movie_data_map.put(word[0], inner_list);
            if (args[0].equals(word[1])) {
                id_of_input = word[0];
                genre_of_input = word[2];
            }
        }
        get_movie_data.close();
        //System.out.println(id_of_input);

        ArrayList<String> user_list = new ArrayList<>();
        ArrayList<String> related_movie = new ArrayList<>();
        HashMap<String,ArrayList<Integer>> movie_rating_map = new HashMap<>();
        BufferedReader get_rating_data = new BufferedReader(new FileReader("data/ratings.dat"));
        while(true){
            String line = get_rating_data.readLine();
            if (line == null)
                break;
            //System.out.println(line);
            String[] word = line.toLowerCase().split("::");
            if(id_of_input.equals(word[1])) {
                user_list.add(word[0]);
            }

            if(movie_rating_map.containsKey(word[1])) {
                int a = movie_rating_map.get(word[1]).get(0) + Integer.parseInt(word[2]);
                movie_rating_map.get(word[1]).set(0, a);
                int b = movie_rating_map.get(word[1]).get(1) + 1;
                movie_rating_map.get(word[1]).set(1, b);
            }
            else {
                ArrayList<Integer> inner_list = new ArrayList<>();
                inner_list.add(Integer.parseInt(word[2]));
                inner_list.add(1);
                movie_rating_map.put(word[1], inner_list);
            }
        }
        get_rating_data = new BufferedReader(new FileReader("data/ratings.dat"));
        while(true){
            String line = get_rating_data.readLine();
            //System.out.println(line);
            if (line == null)
                break;
            //System.out.println(line);
            String[] word = line.toLowerCase().split("::");

            if(user_list.contains(word[0])){
                //System.out.println("test");
                if(!related_movie.contains(word[1])) {
                    related_movie.add(word[1]);
                }
            }
        }
        get_rating_data.close();

        for(Map.Entry<String, ArrayList<String>> Iter : movie_data_map.entrySet()){
            String[] genre = Iter.getValue().get(1).split("\\|");
            String[] genre_of_input_array = genre_of_input.split("\\|");
            ArrayList<String> genre_list = new ArrayList<>(Arrays.asList(genre));
            ArrayList<String> genre_of_input_list = new ArrayList<>(Arrays.asList(genre_of_input_array));
            if(genre_list.containsAll(genre_of_input_list)){
                if(!related_movie.contains(Iter.getKey())) {
                    related_movie.add(Iter.getKey());
                }
            }
        }

        ArrayList<Movie_data_node> movie_data_table = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Integer>> Iter : movie_rating_map.entrySet()) {
            if(related_movie.contains(Iter.getKey())) {
                Movie_data_node inner_class = new Movie_data_node();
                inner_class.setMovieID(Iter.getKey());
                inner_class.setTitle(movie_data_map.get(Iter.getKey()).get(0));
                inner_class.setGenre(movie_data_map.get(Iter.getKey()).get(1));
                inner_class.setTotal_rating(Iter.getValue().get(0));
                inner_class.setCounter(Iter.getValue().get(1));
                movie_data_table.add(inner_class);
            }
        }
        int m = percentile(movie_data_table,0.8);
        double C = total_average_rating(movie_data_table);
        //System.out.println(m);
        ArrayList<Classified_by_vote> classified_table = make_classified_table(movie_data_table, C, m);
        print_output_format(classified_table);






    }

}
