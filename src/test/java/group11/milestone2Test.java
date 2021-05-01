package group11;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static org.junit.jupiter.api.Assertions.*;

public class milestone2Test extends milestone2 {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    //------------------------------------------------------------------

    @Test
    public void age_input_test() throws IOException {
        HashMap<String, ArrayList<String>> user_data = milestone2.make_user_data();

        String[] age_list = new String[]{
                "10", "20", "30", "40", "48", "50", "57"
        };

        for (String age_input : age_list) {
            assertAll( () -> milestone2.make_user_list_age(user_data, age_input));
        }
    }

    @Test
    public void occu_input_test() throws IOException {
        HashMap<String, ArrayList<String>> user_data = milestone2.make_user_data();

        String[] occu_list = new String[]{
                "academic", "artist", "admin", "grad", "customerservice",
                "doctor", "executive", "farmer", "homemaker", "k-12student",
                "lawyer", "programmer", "retired", "marketing", "scientist",
                "self-employed", "engineer", "tradesman", "unemployed",
                "writer", "ARBITRARYINPUT" /*-> Last one falls to occunum 0*/
        };

        for (String occu_input : occu_list) {
            assertAll( () -> milestone2.make_user_list_occu(user_data, occu_input));
        }
    }

    //------------------------------------------------------------------

    String[][] args_list = new String[][]{
            {"A","25","Action"}, /*->Invalid Gender input */
            {"F" ,"-50" ,"Action"}, /*->Invalid Age input */
            {"F" ,"25" ,"Accccion"}, /*->Invalid Genre input e*/
            {"A" ,"THISISNOTAGE","Action"}, /*->Invalid Gender and age input */
            {"A" ,"25","Accccion"}, /*->Invalid Gender and genre input */
            {"F" ,"THISISNOTAGE","Accccion"}, /*->Invalid Age and genre input */
            {"A" ,"THISISNOTAGE","Accccion"} /*->Invalid Gender, age and genre input */
    };

    // Those tests will exit with Status Code 1
    /*
    @Test
    public void invalid_input_test_0() throws IOException {
        milestone2.main(args_list[0]);
        //System.out.printf("outcontent is %s ", outContent.toString());
        assertEquals("Gender input error\n", outContent.toString());
    }
    @Test
    public void invalid_input_test_1() throws IOException {
        milestone2.main(args_list[1]);
        assertEquals("Age input error\n", outContent.toString());
    }
    @Test
    public void invalid_input_test_2() throws IOException {
        milestone2.main(args_list[2]);
        assertEquals("Genre input error\n", outContent.toString());
    }
    @Test
    public void invalid_input_test_3() throws IOException {
        milestone2.main(args_list[3]);
        assertEquals("Gender and age input error\n", outContent.toString());
    }

    @Test
    public void invalid_input_test_4() throws IOException {
        milestone2.main(args_list[4]);
        assertEquals("Gender and genre input error\n", outContent.toString());
    }

    @Test
    public void invalid_input_test_5() throws IOException {
        milestone2.main(args_list[5]);
        assertEquals("Age and genre input error\n", outContent.toString());
    }
    @Test
    public void invalid_input_test_6() throws IOException {
        milestone2.main(args_list[6]);
        assertEquals("Gender, age and genre input error\n", outContent.toString());
    }
*/


    //------------------------------------------------------------------

    @Test
    public void empty_3args_test() {
        String[] args = new String[]{"", "", ""};

        assertAll( () -> milestone2.main(args));
    }

    @Test
    public void valid_3args_test() {
        String[] args = new String[3];
        args[0] = "F";
        args[1] = "56";
        args[2] = "retired";

        assertAll( () -> milestone2.main(args));
    }

    @Test
    public void empty_4args_test() {
        String[] args = new String[]{"", "", "", "Action"};

        assertAll( () -> milestone2.main(args));
    }

    //------------------------------------------------------------------
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}