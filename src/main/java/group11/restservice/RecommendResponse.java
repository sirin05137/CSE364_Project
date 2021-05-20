package group11.restservice;

public class RecommendResponse {
    private String gender;
    private String age;
    private String occupation;
    private String genre;

    public RecommendResponse(String gender, String age, String occupation, String genre) {
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.genre = genre;
    }

}
