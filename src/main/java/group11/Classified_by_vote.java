package group11;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Class inherited from Movie_data_node. It has information of movie link and weighted rating.
// Movies rated above the minimum number of votes will be classified.
public class Classified_by_vote extends Movie_data_node implements Comparable {
    double W = 0;
    String link = "";

    public int compareTo(Object o) {
        return compareTo((Classified_by_vote) o);
    }

    public int compareTo(Classified_by_vote another) {
        double thisVal = this.getW();
        double anotherVal = another.getW();
        return (thisVal < anotherVal ? 1 : (thisVal == anotherVal ? 0 : -1));
    }

    public Classified_by_vote() {
    }

    public Classified_by_vote(Movie_data_node movie_rating_data) {
        this.movieID = movie_rating_data.getMovieID();
        this.title = movie_rating_data.getTitle();
        this.genre = movie_rating_data.getGenre();
        this.total_rating = movie_rating_data.getTotal_rating();
        this.counter = movie_rating_data.getCounter();
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setW(double CC, int mm) {
        if (counter == 0) {
            this.W = CC;
        } else {
            BigDecimal tr = new BigDecimal(String.valueOf(total_rating));
            BigDecimal v = new BigDecimal(String.valueOf(counter));
            BigDecimal R = tr.divide(v, 3, RoundingMode.HALF_UP);
            BigDecimal C = new BigDecimal(String.valueOf(CC));
            BigDecimal m = new BigDecimal(String.valueOf(mm));
            BigDecimal upper = v.multiply(R).add(m.multiply(C));
            BigDecimal lower = v.add(m);
            this.W = upper.divide(lower, 3, RoundingMode.HALF_UP).doubleValue();
        }
    }

    public String getLink() {
        return link;
    }

    public double getW() {
        return W;
    }

    /*public void print_node() {
        System.out.println("{movieID : " + this.movieID + ", Title : " + this.title + ", Genre : " + this.genre + ", link : "
                + this.link + ", total_rating : " + this.total_rating + ", counter : " + this.counter + ", W : " + this.W + "}");
    }*/
}
