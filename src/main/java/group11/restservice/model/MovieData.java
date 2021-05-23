package group11.restservice.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// MOVIE INPUT
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieData {
    private String title;
    private int limit = 10;

    @JsonProperty("title")
    public String getTitle() { return title; }
    @JsonProperty("title")
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("limit")
    public int getLimit() { return limit; }
    @JsonProperty("limit")
    public void setLimit(String value) { this.limit = Integer.parseInt(value); }
}
