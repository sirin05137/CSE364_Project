package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

class Similarity_of_movie implements Comparable{
    String movieID;
    double pearson_coefficient;

    public String getMovieID() {
        return movieID;
    }

    public double getPearson_coefficient() {
        return pearson_coefficient;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public void setPearson_coefficient(double pearson_coefficient) {
        this.pearson_coefficient = pearson_coefficient;
    }

    public Similarity_of_movie(){}
    public Similarity_of_movie(String movieID, double similarity){
        this.movieID = movieID;
        this.pearson_coefficient = similarity;
    }
    public int compareTo(Object o) {
        return compareTo((Similarity_of_movie) o);
    }
    public int compareTo(Similarity_of_movie another) {
        double thisVal = this.getPearson_coefficient();
        double anotherVal = another.getPearson_coefficient();
        return (thisVal < anotherVal ? 1 : (thisVal == anotherVal ? 0 : -1));
    }

}

public class milestone3 {

    //{movieID : [title, genre]}//
    static HashMap<String, ArrayList<String>> make_movie_data_map () throws IOException{
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
        }
        get_movie_data.close();
        return movie_data_map;
    }
    //{movieID : {userID : rating}}//
    static HashMap<String,HashMap<String,Integer>> make_rating_data_map () throws IOException{
        HashMap<String,HashMap<String,Integer>> rating_data_map = new HashMap<>();
        BufferedReader get_rating_data = new BufferedReader(new FileReader("data/ratings.dat"));
        while(true) {
            HashMap<String, Integer> inner_map = new HashMap<>();
            String line = get_rating_data.readLine();
            if (line == null)
                break;
            String[] word = line.split("::");
            if(rating_data_map.containsKey(word[1])){
                rating_data_map.get(word[1]).put(word[0],Integer.parseInt(word[2]));
            }
            else {
                inner_map.put(word[0], Integer.parseInt(word[2]));
                rating_data_map.put(word[1], inner_map);
            }
        }
        get_rating_data.close();
        return rating_data_map;
    }

    static double get_average_rating (HashMap<String,Integer> ratings_of_movie){
        int total_rating = 0;
        for(Map.Entry<String,Integer> Iter : ratings_of_movie.entrySet()){
            total_rating += Iter.getValue();
        }
        BigDecimal A = new BigDecimal(String.valueOf(total_rating));
        BigDecimal B = new BigDecimal(String.valueOf(ratings_of_movie.size()));
        BigDecimal mean = A.divide(B,2,RoundingMode.HALF_UP);
        return mean.doubleValue();
    }
    static double get_distance_of_vector (HashMap<String,Integer> ratings_of_movie, double mean) {
        BigDecimal A = new BigDecimal("0.0");
        for(Map.Entry<String,Integer> Iter : ratings_of_movie.entrySet()){
            BigDecimal B = new BigDecimal(String.valueOf(Iter.getValue()));
            //System.out.println("B : "+B.doubleValue());
            BigDecimal C = new BigDecimal(String.valueOf(mean));
            BigDecimal D = B.subtract(C);
            //System.out.println("D : "+D.doubleValue());
            A = A.add(D.pow(2));
            //System.out.println("A : "+A.doubleValue());
        }
        double sum_of_power = A.doubleValue();
        return Math.sqrt(sum_of_power);
    }
    static double get_pearson_similarity (HashMap<String,Integer> rating, HashMap<String,Integer> rating_of_input, double mean_rating_of_input, double distance_of_input){
        double mean = get_average_rating(rating);
        double distance = get_distance_of_vector(rating,mean);
        if(distance!=0) {
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
            BigDecimal similarity = Z.divide(temp, 2, RoundingMode.HALF_UP);
            return similarity.doubleValue();
        }
        else
            return -99;
    }

    static boolean check_title_validity(String title_input, HashMap<String, ArrayList<String>> movie_data_map){
        String title = title_input.replace(" ", "");
        for(Map.Entry<String,ArrayList<String>> Iter : movie_data_map.entrySet()){
            String A = Iter.getValue().get(0).replace(" ","");
            if(A.toLowerCase().trim().equals(title.toLowerCase().trim())){
                return true;
            }
        }
        return false;
    }
    static boolean check_limit_validity(String limit_input) {
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
        } else {
            return true;
        }
    }


    public static void main(String[] args) throws IOException{

        if(!(args.length == 1 || args.length==2)) {
            System.out.println("InputNumError: The input must be in this format : \"title\" \"limit\" (limit is optional).");
            System.exit(1);
        }

        //{movieID : [title, genre]}//
        HashMap<String, ArrayList<String>> movie_data_map = make_movie_data_map();

        boolean a = check_title_validity(args[0], movie_data_map);
        boolean b = true;
        if(args.length==2){
            b = check_limit_validity(args[1]);
        }
        if(!(a && b)){
            System.exit(1);
        }

        int limit = 10;
        if(args.length==2){
            limit = Integer.parseInt(args[1]);
        }

        //{movieID : {userID : rating}}//
        HashMap<String,HashMap<String,Integer>> rating_data_map = make_rating_data_map();
        //System.out.println(rating_data_map.get("2308"));

        //--------------------------------------

        // 입략받은 타이틀의 ID와 genre를 저장함
        String id_of_input="";
        String genre_of_input="";

        for(Map.Entry<String,ArrayList<String>> Iter : movie_data_map.entrySet()){
            if(Iter.getValue().get(0).replace(" ","").toLowerCase().trim().equals(args[0].replace(" ","").toLowerCase().trim())){
                id_of_input = Iter.getKey();
                genre_of_input = Iter.getValue().get(1);
                break;
            }
        }
        //System.out.println(id_of_input);
        //System.out.println(genre_of_input);

        //--------------------------------------
        //if(rating_data_map.get(id_of_input).size()==0){
            //System.out.println("error");
            //System.exit(1);
        //}
        double mean_rating_of_input = get_average_rating(rating_data_map.get(id_of_input));
        //System.out.println(mean_rating_of_input);
        double distance_of_input = get_distance_of_vector(rating_data_map.get(id_of_input), mean_rating_of_input);
        //System.out.println(denominator);


        ArrayList<Similarity_of_movie> Similarity_table = new ArrayList<>();
        for(Map.Entry<String,HashMap<String,Integer>> Iter : rating_data_map.entrySet()){
            double similarity = get_pearson_similarity(Iter.getValue(), rating_data_map.get(id_of_input), mean_rating_of_input,distance_of_input);
            Similarity_of_movie inner_class = new Similarity_of_movie(Iter.getKey(), similarity);
            Similarity_table.add(inner_class);
        }
        Collections.sort(Similarity_table);
        for(int i = 0; i < 20 ; i++){
            System.out.println(movie_data_map.get(Similarity_table.get(i).getMovieID()).get(0) + " : " + Similarity_table.get(i).getPearson_coefficient());
        }
        System.out.println(Similarity_table.size());
    }

}
