package group11;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class milestone2Test extends milestone2 {


    @Test
    public void valid_genre_test() {
        String[] args = new String[]{"action", "adventure", "animation"};
        assertTrue(milestone2.check_genre_validity(args));
    }

    @Test
    public void invalid_genre_test() {
        String[] args = new String[]{"action", "advvvvventure", "animacccccion"};
        assertFalse(milestone2.check_genre_validity(args));
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

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void exitsWithStatusCode1() throws IOException {
        String[][] args_list = new String[][]{
                {"A","25","Action"}, /*->Invalid gender*/
                {"F" ,"-50" ,"Action"}, /*->Invalid age*/
                {"F" ,"25" ,"Accion"}, /*->Invalid genre*/
                {"A" ,"-50","Action"} /*->Invalid gender&age*/
        };

        exit.expectSystemExitWithStatus(1);
        milestone2.main(args_list[0]);
        /*
        for (String[] args : args_list) {
            exit.expectSystemExitWithStatus(1);
            milestone2.main(args);
        }
        */
    }





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
}