package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


// Class that store information of movies (like movieID, title, genre, total rating, vote counter)
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
// Class inherited from Movie_data_node. It has information of movie link and weighted rating.
// Movies rated above the minimum number of votes will be classified.
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

    public void W_plus_similarity(double similarity){
        BigDecimal w_rating = new BigDecimal(String.valueOf(this.W));
        BigDecimal temp = new BigDecimal(String.valueOf(similarity));
        this.W = w_rating.add(temp).doubleValue();
        //this.W = w_rating.multiply(temp).doubleValue();
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

    private static ArrayList<Classified_by_vote> classified_table = null;

    //Calculating the total average rating of movies classified by user data
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
            System.out.println("NoDBError : There are no movie available for specified inputs.");
            System.exit(1);
        }
        //System.out.println(sum_of_average_rating.doubleValue());
        //System.out.println(c.doubleValue());
        BigDecimal result = sum_of_average_rating.divide(c,2,RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    static double set_p(int size){
        if(size<100){
            return 0.5;
        }
        else if(size<200){
            return 0.6;
        }
        else if(size<500){
            return 0.7;
        }
        else{
            return 0.8;
        }
    }
    //get percentile when ratio is p
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
            if(a==0)
                return vote_counting_list.get(0);
            return (vote_counting_list.get(a-1)+vote_counting_list.get(a))/2;
        }
        else{
            //System.out.println("elsecase");
            //System.out.println(vote_counting_list);
            int b=np.intValue();
            return vote_counting_list.get(b-1);
        }
    }
    //Extract and sort movies with m or more votes.
    public static ArrayList<Classified_by_vote> make_classified_table(ArrayList<Movie_data_node> movie_rating_matrix, double C, int m) throws IOException{
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
        BufferedReader get_link_data = new BufferedReader(new FileReader("/root/project/apache-tomcat-8.5.68/webapps/cse364-project/WEB-INF/classes/links.dat"));
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
    public String get_ith_movieid(int index){
        return classified_table.get(index).getMovieID();
    }


    //Extract only movies that match genres from movies classified by user data
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

    public static boolean check_gender_validity(String gender){
        if(gender.trim().toLowerCase().equals("") || gender.trim().toLowerCase().equals("m") || gender.trim().toLowerCase().equals("f")){
            return true;
        }
        else{
            System.out.println("InputInvalidError : Entered gender input is invalid.");
            return false;
        }
    }
    public static boolean check_age_validity(String age_input){
        String age = age_input.replace(" ", "");
        if(age.trim().equals("")){
            return true;
        }
        else {
            boolean isNumeric = true;
            for(int i = 0;i<age.length();i++){
                if(!Character.isDigit(age.charAt(i))){
                    isNumeric = false;
                }
            }
            if(!isNumeric){
                System.out.println("InputInvalidError : Entered age input is invalid. Age must be a natural number.");
                return false;
            }
            else if(Integer.parseInt(age.trim())<1){
                System.out.println("InputInvalidError : Entered age input is invalid. Age must be a natural number.");
                return false;
            }
            else{
                return true;
            }
        }
    }
    public static boolean check_occu_validity(String occu_input){
        String occu = occu_input.replace(" ", "");
        if(occu.trim().toLowerCase().equals("")){
            return true;
        }
        else {
            String occupationinput = occu.toLowerCase().trim();
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
                    OccupationNumber = "0";
                    break;
                default:
                    OccupationNumber = "100";
            }
            if (OccupationNumber.equals("100")){
                System.out.printf("InputInvalidError : Entered occupation (%s) doesn't exist.\n", occu_input);
                return false;
            }
            else{
                return true;
            }
        }

    }
    public static boolean check_genre_validity(String genre_input){
        String genre = genre_input.replace(" ", "");
        String[] dbgenre= {"action", "adventure", "animation", "children's","comedy","crime","documentary","drama","fantasy","film-noir",
                "horror","musical","mystery","romance","sci-fi","thriller","war","western", "other"};

        String genreinput=genre.toLowerCase().trim();
        String[] multiinput=genreinput.split("\\|");;
        int input_validity_counter = 0;

        //check empty input (eg. "", )
        if (genreinput.trim().length() <=0) {
            //input_validity_counter=-99;//It indicate invalid input. -99 don't have meaning, just mean error.
            System.out.println("InputEmptyError : Genre input hasn't passed. Genre must not be empty");
            //System.out.println("Error code: 1\n");
            //System.exit(1);
            return false;
        }
        // check input has "|" as last character. (eg. "|", "comedy|")
        else {
            if(genreinput.trim().charAt(genreinput.length()-1)=='|') {
                //input_validity_counter=-99;//It indicate invalid input. -99 don't have meaning, just mean error.
                System.out.println("InputInvalidError : Entered genre input is invalid.");
                //System.out.println("Error code: 4\n");
                //System.exit(1);
                return false;
            }
        }
        //check empty input surround '|'  (eg.  "|academic", "academic||engineer")
        for (int i = 0; i < multiinput.length; i++) {
            if (multiinput[i].isBlank()) {
                //System.out.print("\n empty input \n");
                //input_validity_counter=-99; //It indicate invalid input. -99 don't have meaning, just mean error.
                System.out.println("InputInvalidError : Entered genre input is invalid.");
                //System.out.println("Error code: 4\n");
                //System.exit(1);
                return false;
            }
        }

        boolean A;
        String bufer = "";
        for (int i = 0; i < multiinput.length; i++) {
            A=false;
            for(int j=0; j< dbgenre.length; j++) {
                if( multiinput[i].trim().equals(dbgenre[j])) {
                    input_validity_counter++;
                    A=true;
                    //System.out.print("\n genre input matched");
                    break;
                }
            }
            if(!A) {
                bufer += multiinput[i].trim() + ", ";
                //System.out.print("\nINPUT_ERROR: genre input ");
                //System.out.print(multiinput[i]);
                //System.out.print(" is invalid genre. \n");
            }
        }
        if (input_validity_counter != multiinput.length) {
            bufer = bufer.substring(0, bufer.length()-2);
            System.out.printf("InputInvalidError : Entered genre (%s) doesn't exist.\n",bufer);
            //System.out.println("Error code: 5\n");
            //System.exit(1);
            return false;
        }
        return true;
    }

    //Form a map of users using users.dat. {UserID : [gender, age, occupation]}
    static HashMap<String, ArrayList<String>> make_user_data() throws IOException{
        HashMap<String, ArrayList<String>> user_data = new HashMap<>();
        BufferedReader get_user_data = new BufferedReader(new FileReader("/root/project/apache-tomcat-8.5.68/webapps/cse364-project/WEB-INF/classes/users.dat"));
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
    static ArrayList<String> make_user_list_age(HashMap<String, ArrayList<String>> user_data, String args1_input){
        String args1 = args1_input.replace(" ", "");
        ArrayList<String> valid_user_list_age = new ArrayList<>();
        if(!args1.trim().equals("")) {
            String age = "";
            int age_int = Integer.parseInt(args1);
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
    static ArrayList<String> make_user_list_occu(HashMap<String, ArrayList<String>> user_data, String args2_input){
        String args2 = args2_input.replace(" ", "");
        //System.out.println(args2);
        ArrayList<String> valid_user_list_occu = new ArrayList<>();
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
    //Use the four functions above to form a user list that matches the user data entered.
    static ArrayList<String> make_intersection_list_macro(HashMap<String, ArrayList<String>> user_data,String args0,String args1_input,String args2_input){
        ArrayList<String> valid_user_list_gender = make_user_list_gender(user_data,args0);
        ArrayList<String> valid_user_list_age = make_user_list_age(user_data,args1_input);
        ArrayList<String> valid_user_list_occu = make_user_list_occu(user_data,args2_input);

        return make_intersection_list(valid_user_list_gender, valid_user_list_age, valid_user_list_occu);
    }

    // Create a map using movie rating of users categorized based on user data entered.
    static HashMap<String,ArrayList<Integer>> make_movie_rating_map(ArrayList<String> valid_user_list) throws IOException{
        HashMap<String,ArrayList<Integer>> movie_rating_map = new HashMap<>();
        BufferedReader get_rating_data = new BufferedReader(new FileReader("/root/project/apache-tomcat-8.5.68/webapps/cse364-project/WEB-INF/classes/ratings.dat"));
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
    // Change movie rating map to movie data table(List of class Movie_data_node)
    static void map_to_table(HashMap<String,ArrayList<Integer>> movie_rating_map, ArrayList<Movie_data_node> movie_data_table){
        for (Map.Entry<String, ArrayList<Integer>> Iter : movie_rating_map.entrySet()) {
            Movie_data_node inner_class = new Movie_data_node();
            inner_class.setMovieID(Iter.getKey());
            inner_class.setTotal_rating(Iter.getValue().get(0));
            inner_class.setCounter(Iter.getValue().get(1));
            movie_data_table.add(inner_class);
        }
    }
    //Get the movie information from movies.dat
    static void set_movie_data_in_node(ArrayList<Movie_data_node> movie_data_table) throws IOException{
        BufferedReader get_movie_data = new BufferedReader(new FileReader("/root/project/apache-tomcat-8.5.68/webapps/cse364-project/WEB-INF/classes/movies.dat"));
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

        if(!(args.length == 3 || args.length==4)) {
            System.out.println("InputNumError: The input must be in this format : \"gender\" \"age\" \"occupation\" \"genre\" (genre is optional).");
            System.exit(1);
        }

        boolean a = check_gender_validity(args[0]);
        boolean b = check_age_validity(args[1]);
        boolean c = check_occu_validity(args[2]);
        boolean d = true;
        if(args.length==4){
            d = check_genre_validity(args[3]);
        }
        if(!(a && b && c && d)){
            System.exit(1);
        }

        HashMap<String, ArrayList<String>> user_data = make_user_data();

        ArrayList<String> valid_user_list_gender = make_user_list_gender(user_data,args[0]);
        ArrayList<String> valid_user_list_age = make_user_list_age(user_data,args[1]);
        ArrayList<String> valid_user_list_occu = make_user_list_occu(user_data,args[2]);
        ArrayList<String> valid_user_list = make_intersection_list(valid_user_list_gender, valid_user_list_age, valid_user_list_occu);
        ArrayList<String> temp;

        HashMap<String,ArrayList<Integer>> movie_rating_map = make_movie_rating_map(valid_user_list);

        if(args.length==3) {
            ArrayList<Movie_data_node> movie_data_table = new ArrayList<>();
            map_to_table(movie_rating_map, movie_data_table);
            double p = set_p(movie_data_table.size());
            int m = percentile(movie_data_table,p);
            for(int i =1;movie_data_table.size()<100||m<10;i++){
                //System.out.println(m);
                if(i==1){
                    valid_user_list = make_intersection_list_macro(user_data, args[0],"",args[2]);
                }
                else if(i==2){
                    temp = make_intersection_list_macro(user_data,"",args[1], args[2]);
                    valid_user_list.addAll(temp);
                }
                else if(i==3){
                    temp = make_intersection_list_macro(user_data,"","", args[2]);
                    valid_user_list.addAll(temp);
                }
                else if(i==4){
                    temp = make_intersection_list_macro(user_data,args[0], args[1], "");
                    valid_user_list.addAll(temp);
                }
                else if(i==5){
                    temp = make_intersection_list_macro(user_data,"", args[1], "");
                    valid_user_list.addAll(temp);
                }
                else if(i==6){
                    temp = make_intersection_list_macro(user_data,args[0], "", "");
                    valid_user_list.addAll(temp);
                }
                else{
                    temp = make_intersection_list_macro(user_data,"", "", "");
                    valid_user_list.addAll(temp);
                }
                HashSet<String> hashSet = new HashSet<>(valid_user_list);
                valid_user_list.clear();
                valid_user_list = new ArrayList<>(hashSet);
                movie_rating_map.clear();
                movie_rating_map = make_movie_rating_map(valid_user_list);
                movie_data_table.clear();
                map_to_table(movie_rating_map, movie_data_table);
                p = set_p(movie_data_table.size());
                m = percentile(movie_data_table,p);
                if(i==7)
                    break;
            }
            //System.out.println(m);
            set_movie_data_in_node(movie_data_table);
            double C = total_average_rating(movie_data_table);
            //System.out.println(m);
            ArrayList<Classified_by_vote> classified_table = make_classified_table(movie_data_table, C, m);
            milestone2.classified_table = classified_table;
            for (int i = 0; i < 10; i++) {
                System.out.println(classified_table.get(i).getTitle() + " (" + classified_table.get(i).getLink() + ")");
            }
        }
        else if(args.length==4) {
            String genre_no_empty = args[3].replace(" ", "");
            String[] input_genre = genre_no_empty.toLowerCase().split("\\|");
            ArrayList<Movie_data_node> movie_data_table = new ArrayList<>();
            map_to_table(movie_rating_map, movie_data_table);
            set_movie_data_in_node(movie_data_table);
            ArrayList<Movie_data_node> table_classified_by_genre = make_table_with_genre(movie_data_table, input_genre);
            double p = set_p(table_classified_by_genre.size());
            int m = percentile(table_classified_by_genre,p);
            for(int i =1;table_classified_by_genre.size()<50||m<10;i++){
                //System.out.println(m);
                if(i==1){
                    valid_user_list = make_intersection_list_macro(user_data, args[0],"",args[2]);
                }
                else if(i==2){
                    temp = make_intersection_list_macro(user_data,"",args[1], args[2]);
                    valid_user_list.addAll(temp);
                }
                else if(i==3){
                    temp = make_intersection_list_macro(user_data,"","", args[2]);
                    valid_user_list.addAll(temp);
                }
                else if(i==4){
                    temp = make_intersection_list_macro(user_data,args[0], args[1], "");
                    valid_user_list.addAll(temp);
                }
                else if(i==5){
                    temp = make_intersection_list_macro(user_data,"", args[1], "");
                    valid_user_list.addAll(temp);
                }
                else if(i==6){
                    temp = make_intersection_list_macro(user_data,args[0], "", "");
                    valid_user_list.addAll(temp);
                }
                else{
                    temp = make_intersection_list_macro(user_data,"", "", "");
                    valid_user_list.addAll(temp);
                }
                HashSet<String> hashSet = new HashSet<>(valid_user_list);
                valid_user_list.clear();
                valid_user_list = new ArrayList<>(hashSet);
                movie_rating_map.clear();
                movie_rating_map = make_movie_rating_map(valid_user_list);
                movie_data_table.clear();
                map_to_table(movie_rating_map, movie_data_table);
                set_movie_data_in_node(movie_data_table);
                table_classified_by_genre.clear();
                table_classified_by_genre = make_table_with_genre(movie_data_table, input_genre);
                p = set_p(table_classified_by_genre.size());
                m = percentile(table_classified_by_genre,p);
                if(i==7)
                    break;
            }
            //System.out.println(m);
            double C = total_average_rating(table_classified_by_genre);
            //System.out.println(m);
            ArrayList<Classified_by_vote> classified_table = make_classified_table(table_classified_by_genre, C, m);
            milestone2.classified_table = classified_table;
            for (int i = 0; i < 10; i++) {
                System.out.println(classified_table.get(i).getTitle() + " (" + classified_table.get(i).getLink() + ")");
            }
        }
        //long end = System.currentTimeMillis();
        //System.out.println(end-start);
    }
}