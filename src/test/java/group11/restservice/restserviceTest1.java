package group11.restservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import group11.restservice.model.*;
import group11.restservice.exception.*;
import group11.restservice.propertyeditor.MovieDataEditor;
import group11.restservice.propertyeditor.UserDataEditor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class restserviceTest1 {

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
        mdeditor.setAsText("{\"title\":\"Action\"}");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mdeditor.setAsText("{\"gender\"\"F\",,,}"); });
    }

}
