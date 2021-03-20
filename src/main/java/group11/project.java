package group11;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import  java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class project {
    public static void main(String[] args) throws IOException{

        //get occupation from users.dat, and make a hashmap which key means user id and value means occupation.

        HashMap<String, String> Occupation = new HashMap<String, String>();

        BufferedReader getuser = new BufferedReader(new FileReader("/Users/k/K/3학년/CSE364/CSE364_Project/data/users.dat")); // need to change the location.
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
        BufferedReader getmovie = new BufferedReader(new FileReader("/Users/k/K/3학년/CSE364/CSE364_Project/data/movies.dat")); // need to change the location.
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
        BufferedReader getrating = new BufferedReader(new FileReader("/Users/k/K/3학년/CSE364/CSE364_Project/data/ratings.dat")); // need to change the location.
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

        //int i = 0;

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

    }
}
