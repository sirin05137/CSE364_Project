package group11;

import junitparams.JUnitParamsRunner;
import org.junit.After;
import org.junit.Before;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class milestone2Test extends milestone2 {

    //private final String emptyValue = null;

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
    /*
    @Test
    public void average_rating_test(){

        HashMap<String, ArrayList<Integer>> h_map = ;
        double val;
        assertNotNull(milestone2.percentile(h_map, val));
    }
    */
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

    @Test
    public void empty_3args_test() {
        String[] args = new String[3];
        args[0] = "";
        args[1] = "";
        args[2] = "";

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
        String[] args = new String[4];
        args[0] = "";
        args[1] = "";
        args[2] = "";
        args[3] = "Action";

        assertAll( () -> milestone2.main(args));
    }
}