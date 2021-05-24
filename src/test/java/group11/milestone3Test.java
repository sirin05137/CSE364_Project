package group11;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;

public class milestone3Test extends milestone3 {
    @Test
    public void sample_args_test () {
        String[] args = new String[1];
        args[0] = "Hungarian Fairy Tale, A (1987)";
        assertAll( () -> milestone3.main(args));

        String[] args_two = new String[2];
        args_two[0] = "Toy Story (1995)";
        args_two[1] = "5";
        assertAll( () -> milestone3.main(args_two));
        get_classified_table(0);
    }

    @Test
    public void sample_args_test_2 () {
        String[] args = new String[2];
        args[0] = "Hungarian Fairy Tale, A (1987)";
        args[1] = "50";
        assertAll( () -> milestone3.main(args));
    }

    @Test
    public void similarity_of_movie_test () {
        Similarity_of_movie sim = new Similarity_of_movie();
        sim.setMovieID("1234");
        sim.setSimilarity(1.234);
    }

    @Test
    public void check_title_validity () {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        Assertions.assertFalse(check_title_validity("ToiStori", map));
    }

    @Test
    public void check_limit_validity () {
        Assertions.assertFalse(check_limit_validity("abc"));
        Assertions.assertFalse(check_limit_validity("0"));
    }

    @Test
    public void get_smallest_counter () {
        ArrayList<Movie_data_node> movie_data_table = new ArrayList<>();
        get_smallest_counter(movie_data_table);
    }
    /*
    @Test
    public void make_classified_table_with_similarity () throws IOException {
        ArrayList<Movie_data_node> movie_data_table = new ArrayList<>();
        HashMap<String, Double> similarity_map = new HashMap<>();
        make_classified_table_with_similarity(movie_data_table, 5, 1000, similarity_map);
    }
     */
}
