package group11.restservice.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// MOVIE INPUT
@AllArgsConstructor
@NoArgsConstructor
public class MovieData {
    private String title;
    private String limit = "10";

    @JsonProperty("title")
    public String getTitle() { return title; }
    @JsonProperty("title")
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("limit")
    public String getLimit() { return limit; }
    @JsonProperty("limit")
    public void setLimit(String value) { this.limit = value; }

    public String[] getJavaInput() {
        String[] javainput;

        javainput = new String[2];
        javainput[0] = title;
        javainput[1] = limit;

        return javainput;
    }

}
