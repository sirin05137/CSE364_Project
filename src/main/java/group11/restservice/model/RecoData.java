package group11.restservice.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Document
public class RecoData {
    @Id
    private String movieid;
    private String title;
    private String genre;
    private String imdblink;

    //ArrayList<Movie_data_node>
    @JsonProperty("movieid")
    public String getMovieid() { return movieid;}
    public void setMovieid(String value) { this.movieid = value;}

    @JsonProperty("title")
    public String getTitle() { return title; }
    @JsonProperty("title")
    public void setTitle(String value) { this.title = value; }

    @JsonProperty("genre")
    public String getGenre() { return genre; }
    @JsonProperty("genre")
    public void setGenre(String value) { this.genre = value; }

    @JsonProperty("imdblink")
    public String getImdblink() { return imdblink; }
    @JsonProperty("imdblink")
    public void setImdblink(String value) { this.imdblink = value; }

}