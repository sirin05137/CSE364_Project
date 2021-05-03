package group11;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class milestone2Test extends milestone2 {


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

    @Test
    public void gender_validity_valid_test(){
        //String gender = "M";
        assertTrue(milestone2.check_gender_validity(""));
        assertTrue(milestone2.check_gender_validity("M"));
        assertTrue(milestone2.check_gender_validity("F"));
    }

    @Test
    public void gender_validity_invalid_test(){
        String gender = "Female";
        assertFalse(milestone2.check_gender_validity(gender));
    }

    @Test
    public void age_validity_valid_test(){
        assertTrue(milestone2.check_age_validity(""));
        assertTrue(milestone2.check_age_validity("25"));
    }

    @Test
    public void age_validity_invalid_test(){
        assertFalse(milestone2.check_age_validity("twelve"));
        assertFalse(milestone2.check_age_validity("12.4"));
        assertFalse(milestone2.check_age_validity("0"));
    }

    @Test
    public void occu_validity_test(){
        String[] occu_list = new String[]{
                "academic", "artist", "admin", "grad", "customerservice",
                "doctor", "executive", "farmer", "homemaker", "k-12student",
                "lawyer", "programmer", "retired", "marketing", "scientist",
                "self-employed", "engineer", "tradesman", "unemployed",
                "writer", "other" /*-> Last one falls to occunum 0*/
        };

        for (String occu_input : occu_list) {
            assertTrue(milestone2.check_occu_validity(occu_input));
        }

        String invalid_input = "THISISWRONG";
        assertFalse(milestone2.check_occu_validity(invalid_input));
    }

    @Test
    public void genre_validity_invalid_test(){
        //Cannot be empty
        assertFalse(milestone2.check_genre_validity(""));
        //pipeline_error_1
        assertFalse(milestone2.check_genre_validity("comedy|"));
        //pipeline_error_2
        assertFalse(milestone2.check_genre_validity("Adventure||Action"));
        //wrong input(spelling)
        assertFalse(milestone2.check_genre_validity("Adventure|Drama|PSYYYYFI"));
    }

    //------------------------------------------------------------------

    @Test
    public void Classified_by_vote_generator_test(){
        assertNotNull(new Classified_by_vote());
    }

    @Test
    public void Classified_by_vote_setW_test(){
        assertAll( ()-> new Classified_by_vote().setW(3,3));
    }

    //------------------------------------------------------------------
    @Test
    public void setP_test(){
        assertEquals(0.5 , milestone2.set_p(50));
        assertEquals(0.6 , milestone2.set_p(150));
        assertEquals(0.7 , milestone2.set_p(250));
        assertEquals(0.8 , milestone2.set_p(1000));
    }

    @Test
    public void percentile_test(){
        Movie_data_node nodeA = new Movie_data_node();
        nodeA.setMovieID("1");
        nodeA.setTitle("Toy Story");
        nodeA.setGenre("Animation");
        nodeA.setTotal_rating(1234);
        nodeA.setCounter(500);

        assertAll(()-> nodeA.print_node());
    }

    //------------------------------------------------------------------

    @Test
    @ExpectSystemExitWithStatus(1)
    public void total_average_rating_SystemExit_test() {
        Movie_data_node nodeA = new Movie_data_node();
        nodeA.setMovieID("1");
        nodeA.setTitle("Toy Story");
        nodeA.setGenre("Animation");
        nodeA.setTotal_rating(0);
        nodeA.setCounter(0);

        ArrayList<Movie_data_node> matrixA = new ArrayList<>();
        matrixA.add(nodeA);

        milestone2.total_average_rating(matrixA);
    }

    @Test
    @ExpectSystemExitWithStatus(1)
    public void percentile_SystemExit_test() {
        Movie_data_node nodeA = new Movie_data_node();
        nodeA.setMovieID("1");
        nodeA.setTitle("Toy Story");
        nodeA.setGenre("Animation");
        nodeA.setTotal_rating(0);
        nodeA.setCounter(0);

        ArrayList<Movie_data_node> matrixA = new ArrayList<>();
        matrixA.add(nodeA);

        milestone2.percentile(matrixA, 0.8);
    }

    @Test
    @ExpectSystemExitWithStatus(1)
    public void make_classified_table_SystemExit_test() throws IOException {
        Movie_data_node nodeA = new Movie_data_node();
        nodeA.setMovieID("1");
        nodeA.setTitle("Toy Story");
        nodeA.setGenre("Animation");
        nodeA.setTotal_rating(0);
        nodeA.setCounter(0);

        ArrayList<Movie_data_node> matrixA = new ArrayList<>();
        matrixA.add(nodeA);

        milestone2.make_classified_table(matrixA, 0.5, 10);
    }

    @Test
    @ExpectSystemExitWithStatus(1)
    public void main_SystemExit_test() throws IOException {
        String[] args = new String[3];
        args[0] = "Female"; //this is invalid. Should be F or f
        args[1] = "25";
        args[2] = "gradstudent";

        milestone2.main(args);
    }

    //------------------------------------------------------------------

    @Test
    public void empty_4args_test() {
        String[] args = new String[]{"", "", "", "Documentary|War"};

        assertAll( () -> milestone2.main(args));
    }

    @Test
    public void sample_3args_test() {
        String[] args = new String[3];
        args[0] = "M";
        args[1] = "20";
        args[2] = "farmer";

        assertAll( () -> milestone2.main(args));
    }

    @Test
    public void sample_4args_test() {
        String[] args = new String[4];
        args[0] = "F";
        args[1] = "1";
        args[2] = "farmer";
        args[3] = "film-noir";

        assertAll( () -> milestone2.main(args));
    }


}