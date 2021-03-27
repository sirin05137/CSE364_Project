package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

<<<<<<< HEAD
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

=======
>>>>>>> kvvak
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
<<<<<<< HEAD

        //get occupation from users.dat, and make a hashmap which key means user id and value means occupation.
        HashMap<String,String> Occupation =  InputMap("data/users.dat", 3);

=======
        //System.out.println(args[0]);
        //System.out.println(args[1]);

        //get occupation from users.dat, and make a hashmap which key means user id and value means occupation.
        HashMap<String,String> Occupation =  InputMap("data/users.dat", 3);
        //System.out.println(Occupation);

>>>>>>> kvvak
        //get genre from movie.dat, and make a hashmap which key means movie id and value means genre
        HashMap<String,String> Genres = InputMap("data/movies.dat", 2);
        //System.out.println(Genres);

        //rating data read
        //double[][][] rating = new double[21][18][2]; 영화 장르가 2~3개인 경우가 았어서 이 방법은 불가능
        //위에서 만든 해쉬맵을 참조하여 중첩맵을 생성, 중첩맵은 {장르 : {직업 : [평점, counter]}}를 나타냄
        //중첩 map안에 Arraylist가 한번 더 들어가 있는 형태
        //{장르 : {직업 : [평점, counter]}}에서 counter는 평점 average를 구하기 위한 초석. 앞선 중첩된 맵의 평점은 그저 같은 직업의 사람이 같은 장르의 영화에 대한 평점을 다 더한것. 평점/counter = 평균 평점

        HashMap<String, HashMap<String, ArrayList<Integer>>> Rating = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
<<<<<<< HEAD
        BufferedReader getrating = new BufferedReader(new FileReader("./data/ratings.dat")); // need to change the location.
=======
        BufferedReader getrating = new BufferedReader(new FileReader("data/ratings.dat"));
>>>>>>> kvvak

        while (true) {
            HashMap<String, ArrayList<Integer>> innermap = new HashMap<String, ArrayList<Integer>>();
            ArrayList<Integer> innerlist = new ArrayList<Integer>();
            String line = getrating.readLine();
            if (line == null) {
                break;
            }
            String[] word = line.split("::" );

            if(Rating.containsKey(Genres.get(word[1]))){
                if(Rating.get(Genres.get(word[1])).containsKey(Occupation.get(word[0]))){
                    int a = Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).get(0) + Integer.parseInt(word[2]);
                    Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).set(0,a);
                    int b = Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).get(1);
                    Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).set(1,b+1);
                }
                else {
                    Rating.get(Genres.get(word[1])).put(Occupation.get(word[0]), innerlist);
                    innerlist.add(0, Integer.parseInt(word[2]));
                    innerlist.add(1, 1);
                }
            }
            else{
                Rating.put(Genres.get(word[1]), innermap);
                innermap.put(Occupation.get(word[0]), innerlist);
                innerlist.add(0,Integer.parseInt(word[2]));
                innerlist.add(1, 1);
            }
        }
        //System.out.println(Rating);
        //숫자 좀 줄여서 제대로 만들어 지는지 테스트

        /*String[] dboccupation= {"academic","educator","artist","clerical","admin","college student",
                "grad student","customer service","doctor","health care","executive","managerial","farmer","homemaker",
                "K-12 student","lawyer","programmer","retired","sales","marketing","scientist","self-employed",
                "technician","engineer","tradesman","craftsman","unemployed","writer","other"};*/

        /*String[] dboccupation= {"academic","educator","artist","clerical","admin","college student",
                "grad student","customer service","doctor","health care","executive","managerial","farmer","homemaker",
                "K-12 student","lawyer","programmer","retired","sales","marketing","scientist","self-employed",
                "technician","engineer","tradesman","craftsman","unemployed","writer","other"};*/

        //입력에 따른 예외처리 조건문 필요
        Scanner inputreader = new Scanner(System.in);
        String genreinput;
        String[] multiinput;
        boolean A;
<<<<<<< HEAD
        do{
            A = false;
            System.out.print("Enter the genre of the movie for which you want to know the rating : ");
            genreinput = inputreader.nextLine().toLowerCase(); // 파이프라인 인식하도록 수정
            multiinput = genreinput.split("\\|");
            for(Map.Entry<String,HashMap<String,ArrayList<Integer>>> Iter : Rating.entrySet()){
                int counter=0;
                A=true;
                for(int i=0;i<multiinput.length;i++){
                    if(Iter.getKey().contains(multiinput[i])) {
                        counter++;
                    }
                    if(counter== multiinput.length){
                        A=false;
                    }
                }
                if(!A){
                    //System.out.println("test");
                    break;
                }
            }
            if(A){
                System.out.println("\nThere are no movies in the genre corresponding to the input value.\n");
            }
        }while(A);
        //System.out.println(multiinput[0]);
        //String[] word = line.split("::");
        // 2. 장르의 조합이 없을경우 오류문구 출력 + 질문 재출력 -> 완료
        // 3. 대소문자 구분없이 비교
        // 4. 아무것도 입력 안했을때


        System.out.print("\nEnter the occupation : ");
        String occupationinput= inputreader.nextLine().toLowerCase();

        // 3. 띄어쓰기 없어도 인식하게 할것
        // 4. 아무것도 입력 안 했을때



=======
        ArrayList<String> inputlist =new ArrayList<String>();
        //ArrayList<String> uncombinelist = new ArrayList<String>();

        do {
            A = true;
            System.out.print("Enter the genre of the movie for which you want to know the rating : ");
            genreinput = inputreader.nextLine().toLowerCase().trim();
            multiinput = genreinput.split("\\|");

            //System.out.println(multiinput.length);

            for (int i = 0; i < multiinput.length; i++) {
                if (multiinput[i].trim().length() > 0) {
                    //System.out.println("test");
                    if (i == multiinput.length - 1) {

                        ArrayList<String> uncombinelist = new ArrayList<String>();
                        //System.out.println("test2");
                        for(int j=0; j<multiinput.length;j++){
                            inputlist.add(multiinput[j].trim());
                        }
                        //System.out.println(inputlist);
                        for(Map.Entry<String, HashMap<String, ArrayList<Integer>>> Iter : Rating.entrySet()){
                            String[] uncombined = Iter.getKey().split("\\|");
                            for(int k=0;k< uncombined.length;k++){
                                uncombinelist.add(uncombined[k].trim());
                            }
                            if(uncombinelist.containsAll(inputlist)){
                                A=false;
                                break;
                            }
                            uncombinelist.clear();
                        }
                        if(A){
                            System.out.println("\nThere are no movies in the genre corresponding to the input value.\n");
                            inputlist.clear();
                        }
                    }
                }
                else {
                    System.out.println("\nInvalid input\n");
                    break;
                }
            }
        }while(A);

        // 2. 장르의 조합이 없을경우 오류문구 출력 + 질문 재출력 -> 완료
        // 3. 대소문자 구분없이 비교 -> 완료
        // 4. 아무것도 입력 안했을때 -> 완료
        // 5. delimiter랑 장르 사이에 빈칸있을때 -> 완료
        // 6. split bug

        // 대형버그 : adventure는 a를 포함해서 a만 입력해도 인식해버리는 상황 발생 -> 완료

        String occupationinput;
        do{
            System.out.print("\nEnter the occupation : ");
            occupationinput= inputreader.nextLine().toLowerCase().trim();
            if(occupationinput=="") {
                System.out.println("Invalid input!");
            }
        }while(occupationinput=="");
        // 3. 띄어쓰기 없어도 인식하게 할것
        // 4. 아무것도 입력 안 했을때 -> 완료
>>>>>>> kvvak

        String OccupationNumber = null;
        switch (occupationinput){
            case "academic":
            case "Academic":
            case "educator":
            case "Educator":
                OccupationNumber = "1";
                break;
            case "artist":
            case "Artist":
                OccupationNumber = "2";
                break;
            case "clerical":
            case "Clerical":
            case "admin":
            case "Admin":
                OccupationNumber = "3";
                break;
            case "college":
            case "College":
            case "college student":
            case "College student":
            case "grad student":
            case "Grad student":
                OccupationNumber = "4";
                break;
            case "customer service":
            case "Customer service":
                OccupationNumber = "5";
                break;
            case "doctor":
            case "Doctor":
            햣 햣
            case "Health care":
                OccupationNumber = "6";
                break;
            case "executive":
            case "Executive":
            case "managerial":
            case "Managerial":
                OccupationNumber = "7";
                break;
            case "farmer":
            case "Farmer":
                OccupationNumber = "8";
                break;
            case "homemaker":
            case "Homemaker":
                OccupationNumber = "9";
                break;
<<<<<<< HEAD
            case "k-12student":
            case "K-12student":
=======
            case "k-12 student":
>>>>>>> kvvak
                OccupationNumber = "10";
                break;
            case "lawyer":
            case "Lawyer":
                OccupationNumber = "11";
                break;
            case "programmer":
            case "Programmer":
                OccupationNumber = "12";
                break;
            case "retired":
            case "Retired":
                OccupationNumber = "13";
                break;
            case "sales":
            case "Sales":
            case "marketing":
            case "Marketing":
                OccupationNumber = "14";
                break;
            case "scientist":
            case "Scientist":
                OccupationNumber = "15";
                break;
            case "self-employed":
            case "Self-employed":
                OccupationNumber = "16";
                break;
            case "technician":
            case "Technician":
            case "engineer":
            case "Engineer":
                OccupationNumber = "17";
                break;
            case "tradesman":
            case "Tradesman":
            case "craftsman":
            case "Craftsman":
                OccupationNumber = "18";
                break;
            case "unemployed":
            case "Unemployed":
                OccupationNumber = "19";
                break;
            case "writer":
            case "Writer":
                OccupationNumber = "20";
                break;
            case "other":
            case "Other":
            default:
                OccupationNumber = "0";
        }
        //System.out.println(OccupationNumber);

        double fullrating = 0;
        double fullcount = 0;
<<<<<<< HEAD
        for(Map.Entry<String,HashMap<String,ArrayList<Integer>>> Iter : Rating.entrySet()){
            int counter=0;
            for(int i=0;i<multiinput.length;i++){
                if(Iter.getKey().contains(multiinput[i])) {
                    counter++;
                }
                if(counter== multiinput.length){
                    if(Iter.getValue().containsKey(OccupationNumber)){
                        fullrating += (double)Iter.getValue().get(OccupationNumber).get(0);
                        fullcount += (double)Iter.getValue().get(OccupationNumber).get(1);
                    }
                }
            }
        }

=======
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
>>>>>>> kvvak

        //double CalculatedInput=(double)Rating.get(genreinput).get(OccupationNumber).get(0)/(double)Rating.get(genreinput).get(OccupationNumber).get(1);
        double CalculatedInput = fullrating/fullcount;
        System.out.printf("\nThe rating of %s rated by %s : %.2f", genreinput, occupationinput, CalculatedInput);

    }
}
