package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*class fournode{
    static String A, B, C;
    static int D=0;
    static void setABC(String args){
        A=args;
    }
    static void setB(String args){
        B=args;
    }
    static void setC(String args){
        C=args;
    }
    static void counter(){

    }
    //static String get()
}*/

public class project {
    static HashMap<String,String> InputMap(String args, int a) throws IOException{
        HashMap<String,String> givendata = new HashMap<String,String>();
        BufferedReader getdata = new BufferedReader(new FileReader(args));
        while (true){
            String line = getdata.readLine();
            if (line==null) {
                break;
            }
            //System.out.println(line);
            String[] word = line.split("::");
            givendata.put(word[0],word[a].toLowerCase());  //value is string type not integer
        }
        getdata.close();
        return givendata;
    }
    public static void main(String[] args) throws IOException{

    	
        //get occupation from users.dat, and make a hashmap which key means user id and value means occupation.
        HashMap<String,String> Occupation =  InputMap("data/users.dat", 3);

        //get genre from movie.dat, and make a hashmap which key means movie id and value means genre
        HashMap<String,String> Genres = InputMap("data/movies.dat", 2);
        //System.out.println(Genres);


        //rating data read

        //double[][][] rating = new double[21][18][2]; 영화 장르가 2~3개인 경우가 았어서 이 방법은 불가능

        //위에서 만든 해쉬맵을 참조하여 중첩맵을 생성, 중첩맵은 {장르 : {직업 : [평점, counter]}}를 나타넴
        //중첩 map안에 Arraylist가 한번더 들어가 있는 형태
        //{장르 : {직업 : [평점, counter]}}에서 counter는 평점 average를 구하기 위한 초석. 앞선 중첩된 맵의 평점은 그저 ""같은 직업의 사람이 같은 장르의 영화에 대한 평점을 다 더한것"". 평점/counter = 평균 평점

        HashMap<String, HashMap<String, ArrayList<Integer>>> Rating = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
        BufferedReader getrating = new BufferedReader(new FileReader("./data/ratings.dat")); // need to change the location.

        while (true) {
            HashMap<String, ArrayList<Integer>> innermap = new HashMap<String, ArrayList<Integer>>();
            ArrayList<Integer> innerlist = new ArrayList<Integer>();
            String line = getrating.readLine();
            if (line == null) {
                break;
            }
            String[] word = line.split("::" );
            //Rating data format = UserID::MovieID::Rating::Timestamp
            //Genres data format = MovieID::Title::Genres
            //Genres.get(word[1])=Genres.get(movie id)=genres
            //Rating.get(Genres.get(word[1]))=Inner hashmap
            //from below, make hashmap "Rating"
            if(Rating.containsKey(Genres.get(word[1]))){//Rating이라는 해쉬맵이, rating 데이터로부터 읽은 장르를 이미 key로써 가지고 있을 경우
            	//Occupation.get(word[0])=Occupation.get(UserID)=occupation
            	//Rating.get(Genres.get(word[1])).get(Occupation.get(word[0]))=Inner hashmap.get(occupation)=data of inner hash maep = [더해진 평점, counter]
                if(Rating.get(Genres.get(word[1])).containsKey(Occupation.get(word[0]))){//Rating이라는 해쉬맵의 데이터인 inner hash맵이, rating 데이터로부터 읽은 직업을 이미 key로써 가지고 있는 경우
                    int a = Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).get(0) + Integer.parseInt(word[2]); // rating_sum = rating_sum + readed rating
                    Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).set(0,a);
                    int b = Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).get(1); //counter = counter +1
                    Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).set(1,b+1);
                }
                else {//Rating이라는 해쉬맵의 데이터인 Inner hash맵이, rating데이터로부터 읽은 직업을 key로 가지고 있지 않은 경우,
                    Rating.get(Genres.get(word[1])).put(Occupation.get(word[0]), innerlist); //Ineer hash map에 해당 키와 데이터를 만들어주고
                    innerlist.add(0, Integer.parseInt(word[2]));//rating_sum = readed rating (rating 초기화)
                    innerlist.add(1, 1);//counter =1 (data 초기화)
                }
            }
            else{//rating이라는 해쉬맵에 , rating 데이터로부터 읽은 장르를 key로 가지고 있지 않은 경우,
                Rating.put(Genres.get(word[1]), innermap);//Rating 해쉬맵에 해당 키(장르)와 데이터(inner hash map)를 만들어주고
                innermap.put(Occupation.get(word[0]), innerlist); // inner hash map 의 키(직업)과 데이터(inner array)초기화
                innerlist.add(0,Integer.parseInt(word[2])); //rating_sum = readed rating (rating 초기화)
                innerlist.add(1, 1);//counter =1 (data 초기화)
            }

        }
        //System.out.println(Rating);


       /*String[] dboccupation= {"academic","educator","artist","clerical","admin","collegestudent",
                "gradstudent","customerservice","doctor","healthcare","executive","managerial","farmer","homemaker",
                "K-12student","lawyer","programmer","retired","sales","marketing","scientist","self-employed",
                "technician","engineer","tradesman","craftsman","unemployed","writer","other"};
                */
       String[] dbgenre= {"Action", "Adventure", "Animation", "Children's","Comedy","Crime","Documentary","Drama","Fantasy","Film-Noir",
    		   "Horror","Musical","Mystery","Romance","Sci-Fi","Thriller","War","Western")
       }
       
       //////////////////////////////////////
       // check genre input validity start //
       //////////////////////////////////////
       Scanner inputreader = new Scanner(System.in);
       String genreinput;
       String[] multiinput = null;
       boolean A;
       ArrayList<String> inputlist =new ArrayList<String>();
       int input_validity_counter = 0;

       do {
    	   input_validity_counter =0;
           A = true;
           System.out.print("\nEnter the genre of the movie for which you want to know the rating : ");
           genreinput = inputreader.nextLine().toLowerCase();
           genreinput = genreinput.replace(" ", ""); //remove all spaces
           multiinput = genreinput.split("\\|");
           //System.out.print("\nmultiinput.length:");
           //System.out.print(multiinput.length);
           //System.out.print("\n");
           
           //check empty input (eg. "", "|" )
           if (genreinput.length() <=0)
        	   input_validity_counter=-99;//invalid input 임을 표지함. -99 수 자체에는 큰 의미 x
           // check input has "|" as last character.             
           else {
               if(genreinput.charAt(genreinput.length()-1)=='|')
            	   input_validity_counter=-99;//invalid input 임을 표지함. -99 수 자체에는 큰 의미 x
           }
           //check empty input surround '|'  (eg.  "|academic", "academic||engineer")
           for (int i = 0; i < multiinput.length; i++) {
          		if (multiinput[i].isBlank()) {
              		 //System.out.print("\n empty input \n");
          			input_validity_counter=-99; //invalid input 임을 표지함. -99 수 자체에는 큰 의미 x
              		break;
              	}
           }
           
           //comapre input with dbgenre. if input exist in the dbgenre, input_validity_counter++;
           for (int i = 0; i < multiinput.length; i++) {
        	   //System.out.print("\nmultiinput:");
        	   //System.out.print(multiinput[i]);
           		for(int j=0; j< dbgenre.length; j++) {
           			if( multiinput[i].contentEquals(dbgenre[j])) {
           				input_validity_counter++;
           				//System.out.print("\n input_vailidity_counter:");
           				//System.out.print(input_vailidity_counter);
           				//System.out.print("\n");
           				break;
           			}
           		}	
           }
           if (input_validity_counter == multiinput.length) {
        		A=false;
        		 for(int j=0; j<multiinput.length;j++){
                     inputlist.add(multiinput[j]);
                 }
        	}
               if(A){
               	System.out.println("\nINPUT INVALID!.\n");
                   }
       }while(A);
       System.out.println("\nINPUT VALID!.\n");
       /////////////////////////////////////
       // check genre input validity end ///
       ////////////////////////////////////
       
        String OccupationNumber = null;
        switch (occupationinput){
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
        //System.out.println(OccupationNumber);

        double fullrating = 0;
        double fullcount = 0;
        //입력 장르와 입력 직업을 고려해야함. 입력 직업은  if (Iter.getValue().containsKey(OccupationNumber)) 로 옳게 고려 되었음.
        //입력 장르는 
        ArrayList<String> uncombinelists = new ArrayList<String>();
        for(Map.Entry<String,HashMap<String,ArrayList<Integer>>> Iter : Rating.entrySet()) {
            String[] uncombined = Iter.getKey().split("\\|");
            for (int k = 0; k < uncombined.length; k++) {
                uncombinelists.add(uncombined[k].trim());
            }
            if (uncombinelists.containsAll(inputlist)) { 
                if (Iter.getValue().containsKey(OccupationNumber)) {
                    fullrating += (double) Iter.getValue().get(OccupationNumber).get(0);
                    fullcount += (double) Iter.getValue().get(OccupationNumber).get(1);
                }
            }
            uncombinelists.clear();
        }

        //double CalculatedInput=(double)Rating.get(genreinput).get(OccupationNumber).get(0)/(double)Rating.get(genreinput).get(OccupationNumber).get(1);
        double CalculatedInput = fullrating/fullcount;
        System.out.printf("\nThe rating of %s rated by %s : %.2f", genreinput, occupationinput, CalculatedInput);

    }
}