package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static group11.milestone2.*;

class Similarity_of_movie implements Comparable{
    String movieID;
    double similarity;

    public String getMovieID() {
        return movieID;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public Similarity_of_movie(){}
    public Similarity_of_movie(String movieID, double similarity){
        this.movieID = movieID;
        this.similarity = similarity;
    }
    public int compareTo(Object o) {
        return compareTo((Similarity_of_movie) o);
    }
    public int compareTo(Similarity_of_movie another) {
        double thisVal = this.getSimilarity();
        double anotherVal = another.getSimilarity();
        return (thisVal < anotherVal ? 1 : (thisVal == anotherVal ? 0 : -1));
    }

}

public class milestone3 {

    private static ArrayList<Classified_by_vote> classified_table = null;

    private static void set_classified_table(ArrayList<Classified_by_vote> cf){
        milestone3.classified_table = cf;
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

    // Create map of format {movieID : [title, genre]}
    public static HashMap<String, ArrayList<String>> make_movie_data_map() throws IOException {
        HashMap<String, ArrayList<String>> movie_data_map = new HashMap<>();
        BufferedReader get_movie_data = new BufferedReader(new FileReader("data/movies.dat"));
        while (true) {
            ArrayList<String> inner_list = new ArrayList<>();
            String line = get_movie_data.readLine();
            if (line == null)
                break;
            //System.out.println(line);
            String[] word = line.split("::");
            inner_list.add(word[1]);
            inner_list.add(word[2]);
            movie_data_map.put(word[0], inner_list);
        }
        get_movie_data.close();
        return movie_data_map;
    }

    // Create map of format {movieID : {userID : rating}}
    static HashMap<String, HashMap<String, Integer>> make_rating_data_map() throws IOException {
        HashMap<String, HashMap<String, Integer>> rating_data_map = new HashMap<>();
        BufferedReader get_rating_data = new BufferedReader(new FileReader("data/ratings.dat"));
        while (true) {
            HashMap<String, Integer> inner_map = new HashMap<>();
            String line = get_rating_data.readLine();
            if (line == null)
                break;
            String[] word = line.split("::");
            if (rating_data_map.containsKey(word[1])) {
                rating_data_map.get(word[1]).put(word[0], Integer.parseInt(word[2]));
            } else {
                inner_map.put(word[0], Integer.parseInt(word[2]));
                rating_data_map.put(word[1], inner_map);
            }
        }
        get_rating_data.close();
        return rating_data_map;
    }

    // A method that returns the average rating of the movie. It is used to calculate the Pearson correlation coefficient.
    static double get_average_rating(HashMap<String, Integer> ratings_of_movie) {
        int total_rating = 0;
        for (Map.Entry<String, Integer> Iter : ratings_of_movie.entrySet()) {
            total_rating += Iter.getValue();
        }
        BigDecimal A = new BigDecimal(String.valueOf(total_rating));
        BigDecimal B = new BigDecimal(String.valueOf(ratings_of_movie.size()));
        BigDecimal mean = A.divide(B, 2, RoundingMode.HALF_UP);
        return mean.doubleValue();
    }

    // Method used to calculate Pearson's correlation coefficient. Calculates and returns sqrt[sigma{(X_i-X_mean)^2}].
    static double get_distance_of_vector(HashMap<String, Integer> ratings_of_movie, double mean) {
        BigDecimal A = new BigDecimal("0.0");
        for (Map.Entry<String, Integer> Iter : ratings_of_movie.entrySet()) {
            BigDecimal B = new BigDecimal(String.valueOf(Iter.getValue()));
            BigDecimal C = new BigDecimal(String.valueOf(mean));
            BigDecimal D = B.subtract(C);
            A = A.add(D.pow(2));
        }
        double sum_of_power = A.doubleValue();
        return Math.sqrt(sum_of_power);
    }

    // Method to calculate and return Pearson correlation coefficient
    static double get_pearson_similarity(HashMap<String, Integer> rating, HashMap<String, Integer> rating_of_input, double mean_rating_of_input, double distance_of_input) {
        double mean = get_average_rating(rating);
        double distance = get_distance_of_vector(rating, mean);
        if (distance != 0) {
            BigDecimal Z = new BigDecimal("0.0");

            for (Map.Entry<String, Integer> Iter2 : rating.entrySet()) {
                if (rating_of_input.containsKey(Iter2.getKey())) {
                    BigDecimal X_i = new BigDecimal(String.valueOf(Iter2.getValue()));
                    BigDecimal X_mean = new BigDecimal(String.valueOf(mean));
                    BigDecimal Y_i = new BigDecimal(String.valueOf(rating_of_input.get(Iter2.getKey())));
                    BigDecimal Y_mean = new BigDecimal(String.valueOf(mean_rating_of_input));

                    BigDecimal X = X_i.subtract(X_mean);
                    BigDecimal Y = Y_i.subtract(Y_mean);

                    Z = Z.add(X.multiply(Y));
                }
            }
            BigDecimal X_distance = new BigDecimal(String.valueOf(distance));
            BigDecimal Y_distance = new BigDecimal(String.valueOf(distance_of_input));
            BigDecimal temp = X_distance.multiply(Y_distance);
            /*if (temp.doubleValue() == 0) {
                System.out.println("divide 0 error");
                System.out.println(Iter.getKey());
                System.exit(1);
            }*/
            BigDecimal similarity = Z.divide(temp, 3, RoundingMode.HALF_UP);
            return similarity.doubleValue();
        } else
            return -99;
    }

    // Method to calculate and return Jaccard similarity
    static double get_jaccard_similarity(ArrayList<String> movie_data ,ArrayList<String> genre_of_input_list){
        String[] genre = movie_data.get(1).split("\\|");
        ArrayList<String> genre_list = new ArrayList<>(Arrays.asList(genre));

        ArrayList<String> temp1 = new ArrayList<>(genre_of_input_list);
        ArrayList<String> temp = new ArrayList<>(genre_of_input_list);

        //입력받은 영화의 장르와 데이터에 저장된 영화의 장르 사이의 교집합 생성
        temp1.retainAll(genre_list);
        //입력받은 영화의 장르와 데이터에 저장된 영화의 장르 사이의 합집합 생성
        temp.addAll(genre_list);
        HashSet<String> temp2 = new HashSet<>(temp);

        BigDecimal A = new BigDecimal(String.valueOf(temp1.size()));
        BigDecimal B = new BigDecimal(String.valueOf(temp2.size()));
        //(교집합 원소 수 / 합집합 원소 수)를 반환
        return A.divide(B,3,RoundingMode.HALF_UP).doubleValue();
    }

    public static boolean check_title_validity(String title_input, HashMap<String, ArrayList<String>> movie_data_map) {
        String title = title_input.replace(" ", "");
        for (Map.Entry<String, ArrayList<String>> Iter : movie_data_map.entrySet()) {
            String A = Iter.getValue().get(0).replace(" ", "");
            if (A.toLowerCase().trim().equals(title.toLowerCase().trim())) {
                return true;
            }
        }
        System.out.println("InputInvalidError : Entered title is invalid. Please check the title and release year again.");
        return false;
    }

    // Method to check the validity of the input limit
    public static boolean check_limit_validity(String limit_input) {
        String limit = limit_input.replace(" ", "");
        boolean isNumeric = true;
        for (int i = 0; i < limit.length(); i++) {
            if (!Character.isDigit(limit.charAt(i))) {
                isNumeric = false;
            }
        }
        if (!isNumeric) {
            System.out.println("InputInvalidError : Entered limit input is invalid. Limit must be a natural number.");
            return false;
        } else if (Integer.parseInt(limit.trim()) < 1) {
            System.out.println("InputInvalidError : Entered limit input is invalid. Limit must be a natural number.");
            return false;
        }else if(Integer.parseInt(limit.trim()) > 2000){
            System.out.println("InputInvalidError : Entered limit input is invalid. Limit must be less than 2000.");
            return false;
        }else {
            return true;
        }
    }

    // Method that returns the number equivalent to 1.5 times the input limit
    // Used to select more movies with higher similarities than limit and then to select higher ratings in them.
    // (similar to the way companies recruit more people than they recruit and re-evaluate them to select recruiters).
    static int get_larger_limit(int limit){
        BigDecimal one_point_five = new BigDecimal("1.5");
        BigDecimal limit_number = new BigDecimal(String.valueOf(limit));
        limit_number=limit_number.multiply(one_point_five);
        limit_number=limit_number.setScale(0,RoundingMode.CEILING);
        return limit_number.intValue();
    }

    // Used to obtain the ratio p required for the percentile calculation
    // Usually 0.3 is returned, but in some cases it is not
    // The value corresponding to 1-(a/b) is returned.
    static double get_ratio(int a, int b){
        BigDecimal aa = new BigDecimal(String.valueOf(a));
        BigDecimal bb = new BigDecimal(String.valueOf(b));
        BigDecimal temp = aa.divide(bb,1,RoundingMode.UP);
        BigDecimal one = new BigDecimal("1");
        BigDecimal ratio = one.subtract(temp);
        return ratio.doubleValue();
    }

    // A method that creates and returns a list of Movie_data_nodes.
    // The length of the list is usually the same as (limit*1.5) (it may change little by little in genre-based recommendations).
    // Movie_data_node is specified in milestone2
    static ArrayList<Movie_data_node> make_movie_data_table(HashMap<String,HashMap<String,Integer>> rating_data_map,ArrayList<String> related_movie_list, HashMap<String, ArrayList<String>> movie_data_map){
        ArrayList<Movie_data_node> movie_data_table = new ArrayList<>();
        for (int i = 0;i<related_movie_list.size();i++){
            if(rating_data_map.containsKey(related_movie_list.get(i))){
                Movie_data_node inner_class = new Movie_data_node();
                int total_rating=0;
                int count=0;
                for(Map.Entry<String,Integer> Iter : rating_data_map.get(related_movie_list.get(i)).entrySet()){
                    total_rating+=Iter.getValue();
                    count++;
                }
                inner_class.setMovieID(related_movie_list.get(i));
                inner_class.setTitle(movie_data_map.get(related_movie_list.get(i)).get(0));
                inner_class.setGenre(movie_data_map.get(related_movie_list.get(i)).get(1));
                inner_class.setTotal_rating(total_rating);
                inner_class.setCounter(count);
                movie_data_table.add(inner_class);
            }
        }
        return movie_data_table;
    }

    //Extract and sort movies with m or more votes. At this time, the score is (IMDb weighted rating + similarity).
    static ArrayList<Classified_by_vote> make_classified_table_with_similarity(ArrayList<Movie_data_node> movie_rating_matrix, double C, int m, HashMap<String, Double> similarity_map) throws IOException{
        ArrayList<Classified_by_vote> classified_table = new ArrayList<>();
        for(int i=0;i<movie_rating_matrix.size();i++){
            if(movie_rating_matrix.get(i).getCounter()>=m){
                Classified_by_vote inner_data = new Classified_by_vote(movie_rating_matrix.get(i));
                classified_table.add(inner_data);
            }
        }
        /*if(classified_table.size()==0){
            System.out.println("NoDBError : No movie available for more than "+m+" votes.");
            System.exit(1);
        }*/
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
            double similarity = similarity_map.get(classified_table.get(i).getMovieID());
            classified_table.get(i).W_plus_similarity(similarity);
        }
        Collections.sort(classified_table); //내림차순으로 정렬
        return classified_table;
    }

    public static void main(String[] args) throws IOException{

        // input validity check
        if(!(args.length == 1 || args.length==2)) {
            System.out.println("InputNumError: The input must be in this format : \"title\" \"limit\" (limit is optional).");
            System.exit(1);
        }

        //{movieID : [title, genre]}
        HashMap<String, ArrayList<String>> movie_data_map = make_movie_data_map();
        // {movieID : {userID : rating}}
        HashMap<String,HashMap<String,Integer>> rating_data_map = make_rating_data_map();

        // input validity check
        boolean a = check_title_validity(args[0], movie_data_map);
        boolean b = true;
        if(args.length==2){
            b = check_limit_validity(args[1]);
        }
        if(!(a && b)){
            System.exit(1);
        }

        // set limit
        int limit = 10;
        if(args.length==2){
            limit = Integer.parseInt(args[1]);
        }

        //--------------------------------------------------------------------------------------------------------------------

        // Saves the ID and genre of the title entered
        String id_of_input="";
        String genre_of_input="";
        for(Map.Entry<String,ArrayList<String>> Iter : movie_data_map.entrySet()){
            if(Iter.getValue().get(0).replace(" ","").toLowerCase().trim().equals(args[0].replace(" ","").toLowerCase().trim())){
                id_of_input = Iter.getKey();
                genre_of_input = Iter.getValue().get(1);
                break;
            }
        }

        //--------------------------------------------------------------------------------------------------------------------

        int larger_limit = get_larger_limit(limit);

        // When to use Item-based Collaborative Filtering
        if(rating_data_map.containsKey(id_of_input)&&rating_data_map.get(id_of_input).size()>100) {

            HashMap<String, Double> similarity_map = new HashMap<>(); // Created to easily find similarity for each movie
            ArrayList<Similarity_of_movie> movie_similarity_table = new ArrayList<>();

            // Calculate the similarity between the input movie and each movie and add it to the movie_similarity_table.
            // Use Pearson correlation coefficient.
            double mean_rating_of_input = get_average_rating(rating_data_map.get(id_of_input));
            double distance_of_input = get_distance_of_vector(rating_data_map.get(id_of_input), mean_rating_of_input);
            for (Map.Entry<String, HashMap<String, Integer>> Iter : rating_data_map.entrySet()) {
                double movie_similarity = get_pearson_similarity(Iter.getValue(), rating_data_map.get(id_of_input), mean_rating_of_input, distance_of_input);
                Similarity_of_movie inner_class = new Similarity_of_movie(Iter.getKey(), movie_similarity);
                movie_similarity_table.add(inner_class);
                similarity_map.put(Iter.getKey(),movie_similarity);
            }

            Collections.sort(movie_similarity_table);

            // 유사도가 높은 순서로 larger_limit개 만큼의 영화를 추출
            // Extracts as many as (larger_limit) movies in order of higher similarity
            ArrayList<String> related_movie_list = new ArrayList<>();
            for(int i = 1; i<larger_limit+1;i++){
                related_movie_list.add(movie_similarity_table.get(i).getMovieID());
            }

            // Calculate and sort the score of the extracted movie as (IMDb weighted rating + similarity).
            ArrayList<Movie_data_node> movie_data_table = make_movie_data_table(rating_data_map, related_movie_list, movie_data_map);
            int m = percentile(movie_data_table,0);
            double C = total_average_rating(movie_data_table);
            ArrayList<Classified_by_vote> classified_table = make_classified_table_with_similarity(movie_data_table, C, m, similarity_map);
            set_classified_table(classified_table);
            for(int i =0 ; i<limit ; i++){
                System.out.println(classified_table.get(i).getTitle() + " " + classified_table.get(i).getLink() + " " + classified_table.get(i).getW());
            }
        }
        //--------------------------------------------------------------------------------------------------------------------

        // When to use Genre-based Recommendation
        else{
            ArrayList<Similarity_of_movie> genre_similarity_table = new ArrayList<>();

            // Calculate the genre similarity between the input movie and each movie and add it to the movie_similarity_table.
            // Use Jaccard similarity.
            String[] genre_of_input_array = genre_of_input.split("\\|");
            ArrayList<String> genre_of_input_list = new ArrayList<>(Arrays.asList(genre_of_input_array));
            for(Map.Entry<String, ArrayList<String>> Iter : movie_data_map.entrySet()){
                if(rating_data_map.containsKey(Iter.getKey()) && !Iter.getKey().equals(id_of_input)) {
                    double genre_similarity = get_jaccard_similarity(Iter.getValue(), genre_of_input_list);
                    Similarity_of_movie inner_class = new Similarity_of_movie(Iter.getKey(), genre_similarity);
                    genre_similarity_table.add(inner_class);
                }
            }

            Collections.sort(genre_similarity_table);


            // It operates by dividing into 3 cases according to the limit-th similarity and the larger_limit-th similarity.
            ArrayList<String> related_movie_list = new ArrayList<>();
            // If the larger_limitth similarity is not 0.
            // 첫번째부터 larger_limit번째까지를 related_movie_list에 추가하고 larger_limit번째의 유사도와 동일한 유사도를 가진 영화들을 related_movie_list에 추가한다.
            if(genre_similarity_table.get(larger_limit-1).getSimilarity()!=0){
                for(int i = 0 ; i < larger_limit ; i++){
                    related_movie_list.add(genre_similarity_table.get(i).getMovieID());
                }
                for(int i = larger_limit ; genre_similarity_table.get(i).getSimilarity()==genre_similarity_table.get(larger_limit-1).getSimilarity();i++){
                    related_movie_list.add(genre_similarity_table.get(i).getMovieID());
                }
            }
            // If the larger_limit-th similarity is 0, but the limit-th similarity is not 0.
            // 첫번째부터 limit번째까지를 related_movie_list에 추가하고 limit번째의 유사도와 동일한 유사도를 가진 영화들을 related_movie_list에 추가한다.
            else if(genre_similarity_table.get(limit-1).getSimilarity()!=0 && genre_similarity_table.get(larger_limit-1).getSimilarity()==0){
                for(int i = 0 ; i < limit ; i++){
                    related_movie_list.add(genre_similarity_table.get(i).getMovieID());
                }
                for(int i = limit ; genre_similarity_table.get(i).getSimilarity()==genre_similarity_table.get(limit-1).getSimilarity();i++){
                    related_movie_list.add(genre_similarity_table.get(i).getMovieID());
                }
            }
            // When the number of movies with non-zero similarity is less than the limit
            // Movies whose similarity is not 0 are added to related_movie_list.
            // After that, movies with a high rating from movies with a similarity of 0 are added to the related_movie_list as many as the number insufficient for the limit.
            else{
                ArrayList<String> unrelated_movie_list = new ArrayList<>();
                for(int i = 0 ; genre_similarity_table.get(i).getSimilarity()!=0;i++){
                    related_movie_list.add(genre_similarity_table.get(i).getMovieID());
                }
                for(int i =related_movie_list.size();i<genre_similarity_table.size();i++) {
                    unrelated_movie_list.add(genre_similarity_table.get(i).getMovieID());
                }

                ArrayList<Movie_data_node> unrelated_movie_data_table = make_movie_data_table(rating_data_map, unrelated_movie_list, movie_data_map);

                int temp = limit - related_movie_list.size();
                double p = get_ratio(temp, unrelated_movie_data_table.size());
                int m = percentile(unrelated_movie_data_table,p);
                double C = total_average_rating(unrelated_movie_data_table);
                ArrayList<Classified_by_vote> classified_table = make_classified_table(unrelated_movie_data_table, C, m);

                int list_size = related_movie_list.size();
                for(int i =0 ; i<limit-list_size;i++){
                    related_movie_list.add(classified_table.get(i).getMovieID());
                }
            }

            // The score of the extracted movie is calculated and sorted by the IMDb weighted rating.
            ArrayList<Movie_data_node> movie_data_table = make_movie_data_table(rating_data_map, related_movie_list, movie_data_map);
            double p = get_ratio(limit,related_movie_list.size());
            int m = percentile(movie_data_table,p);
            double C = total_average_rating(movie_data_table);
            ArrayList<Classified_by_vote> classified_table = make_classified_table(movie_data_table, C, m);
            set_classified_table(classified_table);

            for(int i =0 ; i<limit ; i++){
                System.out.println(classified_table.get(i).getTitle() + " " + classified_table.get(i).getLink() + " " + classified_table.get(i).getW());
            }
        }
    }
}