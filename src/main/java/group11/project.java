package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class project {
    public static void main(String[] args) throws IOException{

        //get occupation from users.dat, and make a hashmap which key means user id and value means occupation.

        HashMap<String, String> Occupation = new HashMap<String, String>();

        BufferedReader getuser = new BufferedReader(new FileReader("./data/users.dat")); // need to change the location.
        //String linefirst = getuser.readLine();
        while (true){
            String line = getuser.readLine();
            if (line==null) {
                break;
            }
            //System.out.println(line);
            String[] word = line.split("::");
            Occupation.put(word[0],word[3]);  //value is string type not integer
            /*for(int i=0;i<5;i++) {
                System.out.println(word[i]);
            }*/
        }
        getuser.close();
        //System.out.println(Occupation.containsKey("0"));
        //System.out.println(Occupation.containsKey("2"));
        //System.out.println(Occupation);

        //get genre from movie.dat, and make a hashmap which key means movie id and value means genre

        HashMap<String, String> Genres = new HashMap<String, String>();
        BufferedReader getmovie = new BufferedReader(new FileReader("./data/movies.dat")); // need to change the location.
        while (true) {
            String line =getmovie.readLine();
            if(line==null){
                break;
            }
            String[] word = line.split("::");
            Genres.put(word[0],word[2]);
        }
        getmovie.close();
        //System.out.println(Genres);


        //rating data read

        //double[][][] rating = new double[21][18][2]; 영화 장르가 2~3개인 경우가 았어서 이 방법은 불가능

        //위에서 만든 해쉬맵을 참조하여 중첩맵을 생성, 중첩맵은 {장르 : {직업 : [평점, counter]}}를 나타내며 파이프라인을 통한 장르 구분은 실현해놓지 않은 상태
        //중첩 map안에 Arraylist가 한번더 들어가 있는 형태
        //{장르 : {직업 : [평점, counter]}}에서 counter는 평점 average를 구하기 위한 초석. 앞선 중첩된 맵의 평점은 그저 같은 직업의 사람이 같은 장르의 영화에 대한 평점을 다 더한것. 평점/counter = 평균 평점

        HashMap<String, HashMap<String, ArrayList<Integer>>> Rating = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
        //HashMap<String, ArrayList<Integer>> innermap = new HashMap<String, ArrayList<Integer>>();
        //ArrayList<Integer> innerlist = new ArrayList<Integer>();
        BufferedReader getrating = new BufferedReader(new FileReader("./data/ratings.dat")); // need to change the location.
        /*String firstline = getrating.readLine();
        String[] firstlineword = firstline.split("::" );
        Rating.put(Genres.get(firstlineword[1]), innermap);
        innermap.put(Occupation.get(firstlineword[0]), innerlist);
        innerlist.add(0,Integer.parseInt(firstlineword[2]));
        innerlist.add(1, 1);*/


        //innermap.remove(Occupation.get(firstlineword[0]));
        //innerlist.remove(0);
        //innerlist.remove(1);

        //System.out.println(Rating);

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


        //입력에 따른 예외처리 조건문 필요

        Scanner inputreader = new Scanner(System.in);
        System.out.print("Enter the genre of the movie for which you want to know the rating : ");
        String genreinput = inputreader.nextLine();
        System.out.print("\nEnter the occupation : ");
        String occupationinput = inputreader.nextLine();
        //System.out.println((double)Rating.get(genreinput).get(occupationinput).get(0));

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
            case "grad student":
                OccupationNumber = "4";
                break;
            case "customer service":
                OccupationNumber = "5";
                break;
            case "doctor":
            case "health care":
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
            case "K-12 student":
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

        double CalculatedInput=(double)Rating.get(genreinput).get(OccupationNumber).get(0)/(double)Rating.get(genreinput).get(OccupationNumber).get(1);
        //System.out.println(CalculatedInput);
        System.out.printf("\nThe rating of %s rated by %s : %.2f", genreinput, occupationinput, CalculatedInput);

    }
}
