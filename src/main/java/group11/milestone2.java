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
    int total_rating=0;
    int counter=0;

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setGenre(String genre) { this.genre = genre; }
    public void setTotal_rating(int total_rating) {
        this.total_rating = total_rating;
    }
    public void setCounter(int counter) {this.counter = counter;}
    public String getMovieID() {
        return movieID;
    }
    public String getTitle() {
        return title;
    }
    public String getGenre() {
        return genre;
    }
    public int getTotal_rating() {
        return total_rating;
    }
    public int getCounter() {
        return counter;
    }
    public void print_node(){
        System.out.println("{movieID : "+this.movieID+", Title : "+this.title+", Genre : "+this.genre+", total_rating : "
                           +this.total_rating+", counter : "+this.counter+"}");
    }
}
class Classified_by_vote extends Movie_data_node implements Comparable {
    double W = 0;
    String link = "";

    public int compareTo(Object o) {
        return compareTo((Classified_by_vote) o);
    }
    public int compareTo(Classified_by_vote another) {
        double thisVal = this.getW();
        double anotherVal = another.getW();
        return (thisVal < anotherVal ? 1 : (thisVal == anotherVal ? 0 : -1));
    }

    public Classified_by_vote() {
    }

    public Classified_by_vote(Movie_data_node movie_rating_data) {
        this.movieID = movie_rating_data.getMovieID();
        this.title = movie_rating_data.getTitle();
        this.genre = movie_rating_data.getGenre();
        this.total_rating = movie_rating_data.getTotal_rating();
        this.counter = movie_rating_data.getCounter();
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setW(double CC, int mm) {
        if (counter == 0) {
            this.W = CC;
        } else {
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

    /*public void print_node() {
        System.out.println("{movieID : " + this.movieID + ", Title : " + this.title + ", Genre : " + this.genre + ", link : "
                + this.link + ", total_rating : " + this.total_rating + ", counter : " + this.counter + ", W : " + this.W + "}");
    }*/
}

public class milestone2 {

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
    static int percentile(ArrayList<Movie_data_node> movie_rating_matrix, double p){
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
    static ArrayList<Classified_by_vote> make_classified_table(ArrayList<Movie_data_node> movie_rating_matrix, double C, int m) throws IOException{
        ArrayList<Classified_by_vote> classified_table = new ArrayList<>();
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
        return classified_table;
    }
    static void print_output_format(ArrayList<Classified_by_vote> classified_table){
        for (int i = 0; i < 10; i++) {
            System.out.println(classified_table.get(i).getTitle() + " (" + classified_table.get(i).getLink() + ")");
        }
    }
    static ArrayList<Movie_data_node> make_table_with_genre(ArrayList<Movie_data_node> inputlist, String[] input_genre){
        ArrayList<Movie_data_node> result = new ArrayList<>();
        for(int i=0; i<inputlist.size();i++) {
            for (int j = 0; j < input_genre.length; j++) {
                if (inputlist.get(i).getGenre().contains(input_genre[j])) {  //.contains에서 오류발생 가능->dbgenre 배열 만들기
                    result.add(inputlist.get(i));
                    break;
                }
            }
        }
        return result;
    }

    static boolean check_gender_validity(String gender){
        return gender.trim().toLowerCase().equals("") || gender.trim().toLowerCase().equals("m") || gender.trim().toLowerCase().equals("f");
    }
    static boolean check_age_validity(String age){
        if(age.trim().equals("")){
            return true;
        }
        else return Integer.parseInt(age.trim()) >= 1;
    }
    static boolean check_genre_validity(String[] input_genre){
        String[] dbgenre= {"action", "adventure", "animation", "children's","comedy","crime","documentary","drama","fantasy","film-noir",
                "horror","musical","mystery","romance","sci-fi","thriller","war","western", "other"};
        boolean A;
        int input_validity_counter = 0;
        String bufer = "";
        for (int i = 0; i < input_genre.length; i++) {
            A=false;
            for(int j=0; j< dbgenre.length; j++) {
                if( input_genre[i].trim().equals(dbgenre[j])) {
                    input_validity_counter++;
                    A=true;
                    //System.out.print("\n genre input matched");
                    break;
                }
            }
            if(!A) {
                bufer += input_genre[i].trim() + ", ";
                //System.out.print("\nINPUT_ERROR: genre input ");
                //System.out.print(multiinput[i]);
                //System.out.print(" is invalid genre. \n");
            }
        }
        if (input_validity_counter != input_genre.length) {
            bufer = bufer.substring(0, bufer.length()-2);
            System.out.printf("\nInputInvalidError : Entered genre (%s) doesn't exist.\n",bufer);
            //System.out.println("Error code: 5\n");
            //System.exit(1);
            return false;
        }
        return true;
    }

    static HashMap<String, ArrayList<String>> make_user_data() throws IOException{
        HashMap<String, ArrayList<String>> user_data = new HashMap<>();
        BufferedReader get_user_data = new BufferedReader(new FileReader("data/users.dat"));
        while(true){
            ArrayList<String> inner_list = new ArrayList<>();
            String line = get_user_data.readLine();
            if (line == null)
                break;
            //System.out.println(line);
            String[] word = line.toLowerCase().split("::");
            inner_list.add(word[1]);
            inner_list.add(word[2]);
            inner_list.add(word[3]);
            user_data.put(word[0], inner_list);
        }
        get_user_data.close();
        return  user_data;
    }
    static ArrayList<String> make_user_list_gender(HashMap<String, ArrayList<String>> user_data, String args0){
        ArrayList<String> valid_user_list_gender = new ArrayList<>();
        if(!args0.trim().equals("")) {
            String gender = args0.trim().toLowerCase();
            for (Map.Entry<String, ArrayList<String>> Iter : user_data.entrySet()) {
                if (Iter.getValue().get(0).equals(gender)) {
                    valid_user_list_gender.add(Iter.getKey());
                    //user_data.entrySet().remove(Iter);
                }
            }
        }
        else{
            for (Map.Entry<String, ArrayList<String>> Iter : user_data.entrySet()) {
                valid_user_list_gender.add(Iter.getKey());
            }
        }
        return valid_user_list_gender;
    }
    static ArrayList<String> make_user_list_age(HashMap<String, ArrayList<String>> user_data, String args1){
        ArrayList<String> valid_user_list_age = new ArrayList<>();
        if(!args1.trim().equals("")) {
            String age = "";
            int age_int = Integer.parseInt(args1);
            /*if(age_int<1) {
                System.out.println("Invalid age input");
                System.exit(1);
            }*/
            if(age_int <18) {
                age="1";
            }
            else if(age_int<25) {
                age="18";
            }
            else if(age_int<35) {
                age="25";
            }
            else if(age_int<45) {
                age="35";
            }
            else if(age_int<50) {
                age="45";
            }
            else if(age_int<56) {
                age="50";
            }
            else{
                age="56";
            }
            for (Map.Entry<String, ArrayList<String>> Iter : user_data.entrySet()) {
                if (Iter.getValue().get(1).equals(age)) {
                    valid_user_list_age.add(Iter.getKey());
                }
            }
        }
        else{
            for (Map.Entry<String, ArrayList<String>> Iter : user_data.entrySet()) {
                valid_user_list_age.add(Iter.getKey());
            }
        }
        return  valid_user_list_age;
    }
    static ArrayList<String> make_user_list_occu(HashMap<String, ArrayList<String>> user_data, String args2){
        ArrayList<String> valid_user_list_occu = new ArrayList<>();
        //직업 입력시 grad student처럼 중간에 띄어쓰기 있는 경우 처리할 것
        if(!args2.trim().equals("")) {
            String occupationinput = args2.toLowerCase().trim();
            String OccupationNumber = "";

            switch (occupationinput) {
                case "academic":
                case "educator":
                    OccupationNumber = "1";
                    break;
                case "artist":
                    OccupationNumber = "2";
                    break;
                case "clerical":
                case "admin":
                    OccupationNumber = "3";
                    break;
                case "college":
                case "collegestudent":
                case "grad":
                case "gradstudent":
                    OccupationNumber = "4";
                    break;
                case "customerservice":
                    OccupationNumber = "5";
                    break;
                case "doctor":
                case "healthcare":
                    OccupationNumber = "6";
                    break;
                case "executive":
                case "managerial":
                    OccupationNumber = "7";
                    break;
                case "farmer":
                    OccupationNumber = "8";
                    break;
                case "homemaker":
                    OccupationNumber = "9";
                    break;
                case "k-12student":
                    OccupationNumber = "10";
                    break;
                case "lawyer":
                    OccupationNumber = "11";
                    break;
                case "programmer":
                    OccupationNumber = "12";
                    break;
                case "retired":
                    OccupationNumber = "13";
                    break;
                case "sales":
                case "marketing":
                    OccupationNumber = "14";
                    break;
                case "scientist":
                    OccupationNumber = "15";
                    break;
                case "self-employed":
                    OccupationNumber = "16";
                    break;
                case "technician":
                case "engineer":
                    OccupationNumber = "17";
                    break;
                case "tradesman":
                case "craftsman":
                    OccupationNumber = "18";
                    break;
                case "unemployed":
                    OccupationNumber = "19";
                    break;
                case "writer":
                    OccupationNumber = "20";
                    break;
                case "other":
                default:
                    OccupationNumber = "0";
            }
            for (Map.Entry<String, ArrayList<String>> Iter : user_data.entrySet()) {
                if (Iter.getValue().get(2).equals(OccupationNumber)) {
                    valid_user_list_occu.add(Iter.getKey());
                }
            }
        }
        else{
            for (Map.Entry<String, ArrayList<String>> Iter : user_data.entrySet()) {
                valid_user_list_occu.add(Iter.getKey());
            }
        }
        return valid_user_list_occu;
    }
    static ArrayList<String> make_intersection_list(ArrayList<String> A, ArrayList<String> B, ArrayList<String> C){
        B.retainAll(C);
        A.retainAll(B);
        return A;
    }
    // 입력된 사용자 정보를 토대로 분류한 사용자들의 영화 평가를 이용하여 map을 생성
    static HashMap<String,ArrayList<Integer>> make_movie_rating_map(ArrayList<String> valid_user_list) throws  IOException{
        HashMap<String,ArrayList<Integer>> movie_rating_map = new HashMap<>();
        BufferedReader get_rating_data = new BufferedReader(new FileReader("data/ratings.dat"));
        while(true){
            String line = get_rating_data.readLine();
            if (line == null)
                break;
            //System.out.println(line);
            String[] word = line.toLowerCase().split("::");
            if(valid_user_list.contains(word[0])){
                if(movie_rating_map.containsKey(word[1])){
                    int a = movie_rating_map.get(word[1]).get(0)+Integer.parseInt(word[2]);
                    movie_rating_map.get(word[1]).set(0,a);
                    int b = movie_rating_map.get(word[1]).get(1)+1;
                    movie_rating_map.get(word[1]).set(1,b);
                }
                else{
                    ArrayList<Integer> inner_list = new ArrayList<>();
                    inner_list.add(Integer.parseInt(word[2]));
                    inner_list.add(1);
                    movie_rating_map.put(word[1],inner_list);
                }
            }
        }
        get_rating_data.close();
        return movie_rating_map;
    }

    static void set_movie_data_in_node(ArrayList<Movie_data_node> movie_data_table) throws IOException{
        BufferedReader get_movie_data = new BufferedReader(new FileReader("data/movies.dat"));
        while(true){
            String line = get_movie_data.readLine();
            if (line == null)
                break;
            //System.out.println(line);
            String[] word = line.split("::");
            for(int i =0;i<movie_data_table.size();i++){
                if(movie_data_table.get(i).getMovieID().equals(word[0])){
                    movie_data_table.get(i).setTitle(word[1]);
                    movie_data_table.get(i).setGenre(word[2].toLowerCase());
                    break;
                }
            }
        }
        get_movie_data.close();
    }

    public static void main(String[] args) throws IOException {
        //long start = System.currentTimeMillis();

        //System.out.println(args[3].replace(" ", ""));
        boolean a = check_gender_validity(args[0]);
        boolean b = check_age_validity(args[1]);
        boolean c = true;
        if(args.length==4){
            String[] input_genre = args[3].trim().toLowerCase().split("\\|");
            c = check_genre_validity(input_genre);
        }
        if(!a && b && c) {
            System.out.println("Gender input error");
            System.exit(1);
        }
        else if(a && !b && c) {
            System.out.println("Age input error");
            System.exit(1);
        }
        else if(a && b && !c) {
            System.out.println("Genre input error");
            System.exit(1);
        }
        else if(!a && !b && c) {
            System.out.println("Gender and age input error");
            System.exit(1);
        }
        else if(!a && b && !c) {
            System.out.println("Gender and genre input error");
            System.exit(1);
        }
        else if(a && !b && !c) {
            System.out.println("Age and genre input error");
            System.exit(1);
        }
        else if(!a && !b && !c) {
            System.out.println("Gender, age and genre input error");
            System.exit(1);
        }

        HashMap<String, ArrayList<String>> user_data = make_user_data();
        //System.out.println(user_data.size());

        ArrayList<String> valid_user_list_gender = make_user_list_gender(user_data,args[0]);
        ArrayList<String> valid_user_list_age = make_user_list_age(user_data,args[1]);
        ArrayList<String> valid_user_list_occu = make_user_list_occu(user_data,args[2]);

        ArrayList<String> valid_user_list = make_intersection_list(valid_user_list_gender, valid_user_list_age, valid_user_list_occu);


        HashMap<String,ArrayList<Integer>> movie_rating_map = make_movie_rating_map(valid_user_list);


        if(args.length==3) {
            ArrayList<Movie_data_node> movie_data_table = new ArrayList<>();
            for (Map.Entry<String, ArrayList<Integer>> Iter : movie_rating_map.entrySet()) {
                Movie_data_node inner_class = new Movie_data_node();
                inner_class.setMovieID(Iter.getKey());
                inner_class.setTotal_rating(Iter.getValue().get(0));
                inner_class.setCounter(Iter.getValue().get(1));
                //inner_class.setW(C, m);
                movie_data_table.add(inner_class);
            }
            set_movie_data_in_node(movie_data_table);
            double C = total_average_rating(movie_data_table);
            int m = percentile(movie_data_table,0.8);
            ArrayList<Classified_by_vote> classified_table = make_classified_table(movie_data_table, C, m);
            print_output_format(classified_table);
        }
        else if(args.length==4) {
            String[] input_genre = args[3].toLowerCase().split("\\|");
            ArrayList<Movie_data_node> movie_data_table = new ArrayList<>();
            for (Map.Entry<String, ArrayList<Integer>> Iter : movie_rating_map.entrySet()) {
                Movie_data_node inner_class = new Movie_data_node();
                inner_class.setMovieID(Iter.getKey());
                inner_class.setTotal_rating(Iter.getValue().get(0));
                inner_class.setCounter(Iter.getValue().get(1));
                //inner_class.setW(C, m);
                movie_data_table.add(inner_class);
            }
            set_movie_data_in_node(movie_data_table);
            ArrayList<Movie_data_node> table_classified_by_genre = make_table_with_genre(movie_data_table, input_genre);

            double C = total_average_rating(table_classified_by_genre);
            int m = percentile(table_classified_by_genre,0.8);
            ArrayList<Classified_by_vote> classified_table = make_classified_table(table_classified_by_genre, C, m);
            print_output_format(classified_table);
        }


        //System.out.println(movie_rating_map);


        //long end = System.currentTimeMillis();
        //System.out.println(end-start  );
        /*for(int i=0;i<movie_rating_matrix_genre_classified.size();i++){
                movie_rating_matrix_genre_classified.get(i).print_node();
            }*/
        /*for(int i=0;i<classified_table.size();i++){
            classified_table.get(i).print_node();
        }*/
    }
}