package org.example;
import java.io.Serializable;
import java.time.LocalDate;

public class Review implements Serializable, Comparable<Review>{
    private final LocalDate timestamp;
    private final Customer customer;
    private Integer rating;
    private String description;

    public Review(Customer c, Integer r, String d){
        // assume rating given between 0 and 5
        customer = c;
        rating = r;
        description = d;
        timestamp = LocalDate.now();
    }

    public String toString(){
        String stars = "* ".repeat(rating);

        return String.format(
                "%s\n%s\n%s\n%s",
                stars,
                "By : " + customer.getName(),
                "On : " + timestamp,
                "Review : "+description
        );
    }

    @Override
    public int compareTo(Review o) {
        return (this.rating - o.getRating());
    }

    public void modifyReview(Integer r, String d){
        rating = r;
        description = d;
    }

    public int getRating(){
        return rating;
    }
}
