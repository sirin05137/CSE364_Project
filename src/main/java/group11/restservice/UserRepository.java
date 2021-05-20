package group11.restservice;

import com.fasterxml.jackson.annotation.*;

public class UserRepository {
    private String gender;
    private String age;
    private String occupation;
    private String genre;

    @JsonProperty("gender")
    public String getGender() { return gender; }
    @JsonProperty("gender")
    public void setGender(String value) { this.gender = value; }

    @JsonProperty("age")
    public String getAge() { return age; }
    @JsonProperty("age")
    public void setAge(String value) { this.age = value; }

    @JsonProperty("occupation")
    public String getOccupation() { return occupation; }
    @JsonProperty("occupation")
    public void setOccupation(String value) { this.occupation = value; }

    @JsonProperty("genre")
    public String getGenre() { return genre; }
    @JsonProperty("genre")
    public void setGenre(String value) { this.genre = value; }
}
