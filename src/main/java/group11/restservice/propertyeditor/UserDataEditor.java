package group11.restservice.propertyeditor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import group11.restservice.model.UserData;
import org.junit.platform.commons.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

//Encapsulate the logic of converting the JSON parameter given as a String to a UserData object
public class UserDataEditor extends PropertyEditorSupport {

    private ObjectMapper objectMapper;

    public UserDataEditor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isBlank(text)) {
            setValue(null);
        } else {
            UserData ud = new UserData();
            try {
                ud = objectMapper.readValue(text, UserData.class);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setValue(ud);
        }
    }

}
