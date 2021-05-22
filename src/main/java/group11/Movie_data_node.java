package group11;

// Class that store information of movies (like movieID, title, genre, total rating, vote counter)
public class Movie_data_node {
    String movieID;
    String title;
    String genre;
    int total_rating = 0;
    int counter = 0;

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTotal_rating(int total_rating) {
        this.total_rating = total_rating;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getTotal_rating() {
        return total_rating;
    }

    public int getCounter() {
        return counter;
    }

    public void print_node() {
        System.out.println("{movieID : " + this.movieID + ", Title : " + this.title + ", Genre : " + this.genre + ", total_rating : "
                + this.total_rating + ", counter : " + this.counter + "}");
    }
}
