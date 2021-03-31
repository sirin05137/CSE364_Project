package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class project 
{
    static HashMap<String,String> InputMap(String args, int a) throws IOException
    {
        HashMap<String,String> givendata = new HashMap<String,String>();
        BufferedReader getdata = new BufferedReader(new FileReader(args));
        while (true)
        {
            String line = getdata.readLine();
            if (line==null) 
                break;
            //System.out.println(line);
            String[] word = line.split("::");
            givendata.put(word[0],word[a].toLowerCase());  //value is string type not integer
        }
        getdata.close();
        return givendata;
    }
    public static void main(String[] args) throws IOException
    {

        if (args.length != 2)
        {
            System.out.println("\nINPUT_ERROR: 2 arguments needed (inputStr1 inputStr2)\n");
            System.exit(0);
        }
        //System.out.println(args[0]);
        //System.out.println(args[1]);

        //get occupation from users.dat, and make a hashmap which key means user id and value means occupation.
        HashMap<String,String> Occupation =  InputMap("data/users.dat", 3);
        //System.out.println(Occupation);

        //get genre from movie.dat, and make a hashmap which key means movie id and value means genre
        HashMap<String,String> Genres = InputMap("data/movies.dat", 2);
        //System.out.println(Genres);

        //rating data read
        //double[][][] rating = new double[21][18][2]; 영화 장르가 2~3개인 경우가 았어서 이 방법은 불가능
        //위에서 만든 해쉬맵을 참조하여 중첩맵을 생성, 중첩맵은 {장르 : {직업 : [평점, counter]}}를 나타냄
        //중첩 map안에 Arraylist가 한번 더 들어가 있는 형태
        //{장르 : {직업 : [평점, counter]}}에서 counter는 평점 average를 구하기 위한 초석. 앞선 중첩된 맵의 평점은 그저 같은 직업의 사람이 같은 장르의 영화에 대한 평점을 다 더한것. 평점/counter = 평균 평점

        HashMap<String, HashMap<String, ArrayList<Integer>>> Rating = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
        BufferedReader getrating = new BufferedReader(new FileReader("data/ratings.dat"));

        while (true) 
        {
            HashMap<String, ArrayList<Integer>> innermap = new HashMap<String, ArrayList<Integer>>();
            ArrayList<Integer> innerlist = new ArrayList<Integer>();
            String line = getrating.readLine();
            if (line == null) 
                break;
            String[] word = line.split("::" );

            if(Rating.containsKey(Genres.get(word[1])))
            {
                if(Rating.get(Genres.get(word[1])).containsKey(Occupation.get(word[0])))
                {
                    int a = Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).get(0) + Integer.parseInt(word[2]);
                    Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).set(0,a);
                    int b = Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).get(1);
                    Rating.get(Genres.get(word[1])).get(Occupation.get(word[0])).set(1,b+1);
                }
                else 
                {
                    Rating.get(Genres.get(word[1])).put(Occupation.get(word[0]), innerlist);
                    innerlist.add(0, Integer.parseInt(word[2]));
                    innerlist.add(1, 1);
                }
            }
            else
            {
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
String[] dbgenre= {"action", "adventure", "animation", "children's","comedy","crime","documentary","drama","fantasy","film-Noir",
    		   "horror","musical","mystery","romance","sci-Fi","thriller","war","western"};
        //입력에 따른 예외처리 조건문 필요
        //Scanner inputreader = new Scanner(System.in);
         //////////////////////////////////////
       // check genre input validity start //
       //////////////////////////////////////
       //Scanner inputreader = new Scanner(System.in);

       String genreinput=args[0].toLowerCase().trim();;
       String[] multiinput = null;
       boolean A;
       ArrayList<String> inputlist =new ArrayList<String>();
       int input_validity_counter = 0;
       
       do 
       {
    	   input_validity_counter =0;
           A = true;
           //System.out.print("\nEnter the genre of the movie for which you want to know the rating : ");
           //genreinput = inputreader.nextLine().toLowerCase();
           //genreinput = genreinput.replace(" ", ""); //remove all spaces
           multiinput = genreinput.split("\\|");
           //System.out.print("\nmultiinput.length:");
           //System.out.print(multiinput.length);
           //System.out.print("\n");
           
           //check empty input (eg. "", "|" )
           if (genreinput.trim().length() <=0)
        	   {
        	   	input_validity_counter=-99;//invalid input 임을 표지함. -99 수 자체에는 큰 의미 x
        		System.out.println("\nINPUT_ERROR: Emtpy input! .\n");
        		System.exit(0);
        	   }
           // check input has "|" as last character.             
           else 
           {
               if(genreinput.trim().charAt(genreinput.length()-1)=='|')
               { 
            	   input_validity_counter=-99;//invalid input 임을 표지함. -99 수 자체에는 큰 의미 x
            	   System.out.println("\nINPUT_ERROR: | is invalid! \n");
            	   System.exit(0);
               }
           }
           //check empty input surround '|'  (eg.  "|academic", "academic||engineer")
           for (int i = 0; i < multiinput.length; i++) 
           {
          		if (multiinput[i].isBlank()) 
          		{
              		 //System.out.print("\n empty input \n");
          			input_validity_counter=-99; //invalid input 임을 표지함. -99 수 자체에는 큰 의미 x
          			System.out.println("\nINPUT_ERROR: input | is invalid! \n");
          			System.exit(0);
              		break;
              	}
           }
           
           //comapre input with dbgenre. if input exist in the dbgenre, input_validity_counter++;
           for (int i = 0; i < multiinput.length; i++) 
           {
           		for(int j=0; j< dbgenre.length; j++) 
           		{
           			if( multiinput[i].contentEquals(dbgenre[j])) 
           			{
           				input_validity_counter++;
           				//System.out.print("\n genre input matched");
           				break;
           			}
           		}
           		if(input_validity_counter != i+1)
           		{
           			System.out.print("\nINPUT_ERROR: genre input ");
           			System.out.print(multiinput[i]);
           			System.out.print(" is invalid genre. \n");
       				System.exit(0);
           		}
           }
           if (input_validity_counter == multiinput.length) 
           {
        	   A=false;
        	   for(int j=0; j<multiinput.length;j++)
        	   {
        		   inputlist.add(multiinput[j].trim());
               }
           }
       }while(A);
       
       /////////////////////////////////////
       // check genre input validity end ///
       ////////////////////////////////////

        // 2. 장르의 조합이 없을경우 오류문구 출력 + 질문 재출력 -> 완료
        // 3. 대소문자 구분없이 비교 -> 완료
        // 4. 아무것도 입력 안했을때 -> 완료
        // 5. delimiter랑 장르 사이에 빈칸있을때 -> 완료
        // 6. split bug

        // 대형버그 : adventure는 a를 포함해서 a만 입력해도 인식해버리는 상황 발생 -> 완료

        String occupationinput = args[1].toLowerCase().trim();
        String OccupationNumber = null;
        
        switch (occupationinput)
        {
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

        //해당하는 직업군 이름이 없을 경우, 경고문구와 함께 others 점수 출력 
        if (OccupationNumber == "0")
        {
            //if(occupationinput != "other")
            if(!occupationinput.trim().equals("other"))
        	{
            	System.out.println("\nINPUT_WWAN: Entered occupation doesn't exist in DB. shown rating is rated by other.\n");
        	}
        }

        double fullrating = 0;
        double fullcount = 0;
        ArrayList<String> uncombinelists = new ArrayList<String>();
        for(Map.Entry<String,HashMap<String,ArrayList<Integer>>> Iter : Rating.entrySet()) 
        {
            String[] uncombined = Iter.getKey().split("\\|");
            for (int k = 0; k < uncombined.length; k++) 
            {
                uncombinelists.add(uncombined[k].trim());
            }
            if (uncombinelists.containsAll(inputlist)) 
            {
                if (Iter.getValue().containsKey(OccupationNumber)) 
                {
                    fullrating += (double) Iter.getValue().get(OccupationNumber).get(0);
                    fullcount += (double) Iter.getValue().get(OccupationNumber).get(1);
                }
            }
            uncombinelists.clear();
        }

        //double CalculatedInput=(double)Rating.get(genreinput).get(OccupationNumber).get(0)/(double)Rating.get(genreinput).get(OccupationNumber).get(1);
        if(fullcount!=0)
        {
        	double CalculatedInput = fullrating/fullcount;
        	System.out.printf("\nThe rating of %s rated by %s : %.2f", genreinput, occupationinput, CalculatedInput);
        }
        else
        {
        	System.out.println("\nNO DATA: Your genre input is valid. but there is no rating which has all matched genres and occupation.\n");
        }
        System.out.println("");
    }
}
