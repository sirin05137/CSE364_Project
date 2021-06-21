package group11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        if (args.length != 2) {
            if (args.length == 0){
                System.out.println("\nInputEmptyError : No argument has passed. 2 arguments are required. (InputStr1 InputStr2)");
                System.out.println("Error code: 1\n");
                System.exit(1);
            }
            else if (args.length == 1){
                System.out.println("\nInputNumError : Only 1 input has passed. 2 arguments are required.");
                System.out.println("Error code: 2\n");
                System.exit(1);
            }
            else {
                System.out.println("\nInputNumError : More than 2 arguments have passed. 2 arguments are required.");
                System.out.println("Error code: 3\n");
                System.exit(1);
            }
        }
        //System.out.println(args[0]);
        //System.out.println(args[1]);

        //get occupation from users.dat, and make a hashmap which key means user id and value means occupation.
        HashMap<String,String> Occupation =  InputMap("/root/project/apache-tomcat-8.5.68/webapps/cse364-project/WEB-INF/classes/users.dat", 3);
        //System.out.println(Occupation);

        //get genre from movie.dat, and make a hashmap which key means movie id and value means genre
        HashMap<String,String> Genres = InputMap("/root/project/apache-tomcat-8.5.68/webapps/cse364-project/WEB-INF/classes/movies.dat", 2);
        //System.out.println(Genres);

        //rating data read
        //Making nested hashmap which consist of {genre : {occupation : [rating, counter]}}
        //Its shape is nested hashmap which contain arraylist.
        //At {genre : {occupation : [rating, counter]}} shape, rating/counter = average rating.

        HashMap<String, HashMap<String, ArrayList<Integer>>> Rating = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
        BufferedReader getrating = new BufferedReader(new FileReader("/root/project/apache-tomcat-8.5.68/webapps/cse364-project/WEB-INF/classes/ratings.dat"));

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

        String[] dbgenre= {"action", "adventure", "animation", "children's","comedy","crime","documentary","drama","fantasy","film-noir",
                            "horror","musical","mystery","romance","sci-fi","thriller","war","western", "other"};

        //Scanner inputreader = new Scanner(System.in);
        //////////////////////////////////////
        // check genre input validity start //
        //////////////////////////////////////
        //Scanner inputreader = new Scanner(System.in);

        String genreinput=args[0].toLowerCase().trim();
        String[] multiinput;
        ArrayList<String> inputlist =new ArrayList<String>();
        int input_validity_counter = 0;

        //System.out.print("\nEnter the genre of the movie for which you want to know the rating : ");
        //genreinput = inputreader.nextLine().toLowerCase();
        //genreinput = genreinput.replace(" ", ""); //remove all spaces
        multiinput = genreinput.split("\\|");
        //System.out.print("\nmultiinput.length:");
        //System.out.print(multiinput.length);
        //System.out.print("\n");
        /*for (int i = 0; i < multiinput.length; i++) {
            System.out.print("\""+multiinput[i]+"\"" + " ");
        }*/

        //check empty input (eg. "", )
        if (genreinput.trim().length() <=0) {
            input_validity_counter=-99;//It indicate invalid input. -99 don't have meaning, just mean error.
            System.out.println("\nInputEmptyError : No argument has passed. 2 arguments are required. (InputStr1 InputStr2)");
            System.out.println("Error code: 1\n");
            System.exit(1);
        }
        // check input has "|" as last character. (eg. "|", "comedy|")
        else {
            if(genreinput.trim().charAt(genreinput.length()-1)=='|') {
                input_validity_counter=-99;//It indicate invalid input. -99 don't have meaning, just mean error.
                System.out.println("\nInputInvalidError : Entered genre input is invalid.");
                System.out.println("Error code: 4\n");
                System.exit(1);
            }
        }
        //check empty input surround '|'  (eg.  "|academic", "academic||engineer")
        for (int i = 0; i < multiinput.length; i++) {
            if (multiinput[i].isBlank()) {
                //System.out.print("\n empty input \n");
                input_validity_counter=-99; //It indicate invalid input. -99 don't have meaning, just mean error.
                System.out.println("\nInputInvalidError : Entered genre input is invalid.");
                System.out.println("Error code: 4\n");
                System.exit(1);
                break;
            }
        }
           
        //comapre input with dbgenre. if input exist in the dbgenre, input_validity_counter++;
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
        if (input_validity_counter == multiinput.length) {
            for(int j=0; j<multiinput.length;j++) {
                inputlist.add(multiinput[j].trim());
            }
        }
        else{
            bufer = bufer.substring(0, bufer.length()-2);
            System.out.printf("\nInputInvalidError : Entered genre (%s) doesn't exist.\n",bufer);
            System.out.println("Error code: 5\n");
            System.exit(1);
        }
        /////////////////////////////////////
        // check genre input validity end ///
        ////////////////////////////////////

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

        //If there is no corresponding occupational name, "other" rating will be printed along with a warning phrase.
        int occuIsInvalid = 0;
        if (OccupationNumber == "0")
        {
            if(!occupationinput.trim().equals("other"))
        	{
            	occuIsInvalid = 1;
        	    System.out.println("\nInputInvalidWarning : Entered occupation doesn't exist. Rating by 'other' is shown instead.");
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
        //System.out.println(fullcount);
        //System.out.println(fullrating);
        if(fullcount!=0)
        {
            double CalculatedInput = fullrating/fullcount;
            if (occuIsInvalid == 1){ // InputInvalidWarning
                System.out.printf("The rating of %s rated by other : %.2f", genreinput, CalculatedInput);
            }
            else {
                System.out.printf("The rating of %s rated by %s : %.2f", genreinput, occupationinput, CalculatedInput);
            }
            }
        else
        {
        	System.out.println("\nNoDBError : Rating data matching the input pair doesn't exist.");
            System.out.println("Error code: 7\n");
            System.exit(1);
        }
        System.out.println("");
    }
}
