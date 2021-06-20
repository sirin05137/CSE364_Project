package group11.restservice.propertyeditor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import group11.restservice.model.MovieData;
import group11.restservice.model.UserData;
import lombok.SneakyThrows;
import org.junit.platform.commons.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

//Encapsulate the logic of converting the JSON parameter given as a String to a UserData object
public class MovieDataEditor extends PropertyEditorSupport {

    private ObjectMapper objectMapper;

    public MovieDataEditor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isBlank(text)) {
            setValue(null);
        } else {
            MovieData md = new MovieData();
            try {
                md = objectMapper.readValue(text, MovieData.class);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            }
            setValue(md);
        }
    }

}
