package group11.restservice.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoData {
    private String title;
    private String genre;
    private String imdb;

        //ArrayList<Movie_data_node>
    @JsonProperty("title")
    public String getTitle() { return title; }
    @JsonProperty("title")
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("genre")
    public String getGenre() { return genre; }
    @JsonProperty("genre")
    public void setGenre(String value) { this.genre = value; }

    @JsonProperty("imdb")
    public String getImdb() { return imdb; }
    @JsonProperty("imdb")
    public void setImdb(String value) { this.imdb = value; }

}