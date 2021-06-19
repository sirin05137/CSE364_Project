package group11;

import com.fasterxml.jackson.databind.ObjectMapper;
import group11.restservice.controller.HelloController;
import group11.restservice.controller.MovieDataController;
import group11.restservice.controller.UserDataController;
import group11.restservice.exception.ApiError;
import group11.restservice.exception.ApiExceptionHandler;
import group11.restservice.exception.InputInvalidException;
import group11.restservice.model.MovieData;
import group11.restservice.model.RecoData;
import group11.restservice.model.UserData;
import group11.restservice.propertyeditor.MovieDataEditor;
import group11.restservice.propertyeditor.UserDataEditor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.WebDataBinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;

public class milestone3Test extends milestone3 {
    /*
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
    public void sample_args_test_3 () {
        String[] args = new String[2];
        args[0] = "Hungarian Fairy Tale, A (1987)";
        args[1] = "100";
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
    public void sample_args_test_4 () {
        String[] args = new String[1];
        args[0] = "Prince of Central Park, The (1999)";
        assertAll( () -> milestone3.main(args));
    }
    @Test
    public void sample_args_test_5 () {
        String[] args = new String[2];
        args[0] = "Prince of Central Park, The (1999)";
        args[1] = "100";
        assertAll( () -> milestone3.main(args));
    }
    @Test
    public void sample_args_test_6 () {
        String[] args = new String[2];
        args[0] = "Prince of Central Park, The (1999)";
        args[1] = "1200";
        assertAll( () -> milestone3.main(args));
    }
    @Test
    public void sample_args_test_7 () {
        String[] args = new String[2];
        args[0] = "Prince of Central Park, The (1999)";
        args[1] = "2000";
        assertAll( () -> milestone3.main(args));
    }


    //---------------------------------------------------------------------------------------------------------

    @Test
    public void udtest ()
    {
        UserData ud = new UserData();
        ud.setGender("M");
        ud.setAge("10");
        ud.setOccupation("Doctor");
        ud.setGenre("");
        String[] actual = {"M","10","Doctor"};
        String[] result = ud.getJavaInput();
        Assertions.assertEquals(Arrays.toString(result), Arrays.toString(actual));

        UserData ud2 = new UserData("F", "25", "Grad", "Action");
        Assertions.assertEquals(ud2.getGender(), "F");
        Assertions.assertEquals(ud2.getAge(), "25");
        Assertions.assertEquals(ud2.getOccupation(), "Grad");
        Assertions.assertEquals(ud2.getGenre(), "Action");
        String[] actual2 = {"F","25","Grad", "Action"};
        String[] result2 = ud2.getJavaInput();
        Assertions.assertEquals(Arrays.toString(result2), Arrays.toString(actual2));
    }

    @Test
    public void mdtest () {
        MovieData md = new MovieData();
        MovieData md2 = new MovieData("Another Title", "50");
        md.setTitle("Title");
        md.setLimit(20);
        Assertions.assertEquals(md.getTitle(), "Title");
        Assertions.assertEquals(md.getLimit(), "20");
        String[] actual = {"Title","20"};
        String[] result = md.getJavaInput();
        Assertions.assertEquals(Arrays.toString(result), Arrays.toString(actual));
    }

    @Test
    public void rdtest () {
        RecoData rd = new RecoData();
        RecoData rd2 = new RecoData("Title", "Genre", "www.google");
        rd.setTitle("T");
        rd.setGenre("G");
        rd.setImdb("W");
        Assertions.assertEquals(rd.getTitle(), "T");
        Assertions.assertEquals(rd.getGenre(), "G");
        Assertions.assertEquals(rd.getImdb(), "W");
    }

    @Test
    public void apierrorTest () {
        ArrayList<String> msg = new ArrayList<>();
        msg.add("Hello");
        ApiError apiError = new ApiError("Errorname", msg);
        apiError.setError("InputInvalidError");
        apiError.setMessage(msg);
        Assertions.assertEquals(apiError.getError(), "InputInvalidError");
        String response = String.valueOf(apiError.getMessage())
                .replace("[", "").replace("]", "");
        Assertions.assertEquals(response, "Hello");

        msg.add("there");
        ApiError apiError2 = new ApiError("InputInvalidError", msg.toString());
        apiError2.setMessage(msg.toString());
    }

    @Test
    public void apiexceptionhandlerTest() {
        ArrayList<String> msg = new ArrayList<>();
        msg.add("Hello");
        msg.add("there");

        InputInvalidException ex = new InputInvalidException(msg);
        Assertions.assertEquals(msg.toString(), ex.getMessage());
        ApiExceptionHandler handler = new ApiExceptionHandler();
        Assertions.assertNotNull(handler.handleException(ex));
    }

    @Test
    public void udeditorTest(){
        ObjectMapper objectMapper = new ObjectMapper();
        UserDataEditor udeditor = new UserDataEditor(objectMapper);
        udeditor.setAsText("");
        udeditor.setAsText("{\"genre\":\"Action\"}");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            udeditor.setAsText("{\"gender\"\"F\",,,}"); });
    }
    @Test
    public void mdeditorTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        MovieDataEditor mdeditor = new MovieDataEditor(objectMapper);
        mdeditor.setAsText("");
        mdeditor.setAsText("{\"title\":\"Action\"}");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mdeditor.setAsText("{\"gender\"\"F\",,,}"); });
    }

    //---------------------------------------------------------------------------------------------------------

    @Test
    public void contextLoads() {
        String response = "";
        HelloController controller = new HelloController();
        response = controller.helloWorld();

        Assertions.assertEquals(response, "Hello World!");
        //assertThat(controller).isNotNull();
        //Assertions.assertEquals(controller.helloWorld(),"Hello World!");
        //System.out.println(controller.helloWorld());

    }

    @SneakyThrows
    @Test
    public void UDcontrollerTest_1 () {
        UserDataController udcontroller = new UserDataController();

        String attributeName = "sessionAttr";
        String attribute = "value";
        WebDataBinder binder = new WebDataBinder(attribute, attributeName);
        udcontroller.initBinder(binder);

        UserData ud = new UserData("F", "25", "Grad", "Action");
        Assertions.assertEquals(udcontroller.addUserData(ud),ud);
    }

    @Test
    public void MDcontrollerTest_1 () {
        MovieDataController mdcontroller = new MovieDataController();

        String attributeName = "sessionAttr";
        String attribute = "value";
        WebDataBinder binder = new WebDataBinder(attribute, attributeName);
        mdcontroller.initBinder(binder);

        MovieData md = new MovieData("Title", "20");
        Assertions.assertEquals(mdcontroller.addMovieData(md),md);

    }

    @Test
    public void UDcontrollerTest_2 () {
        UserDataController udcontroller = new UserDataController();

        //String request = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ud);
        //System.out.println(request);

        String request = "{}";
        //Assertions.assertAll(udcontroller.getUserRecommendations(request));
    }

    */
}
