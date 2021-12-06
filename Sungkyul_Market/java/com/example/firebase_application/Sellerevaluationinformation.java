package com.example.firebase_application;

import java.util.HashMap;
import java.util.Map;

public class Sellerevaluationinformation {
    public String postname;
    public String review;
    public String rating;
    public String buyeremail;
    public String selleremail;

    public Sellerevaluationinformation() {

    }

    public Sellerevaluationinformation(String postname, String review, String rating, String buyeremail, String selleremail, String buttoncheck) {
        this.postname = postname;
        this.review = review;
        this.rating = rating;
        this.buyeremail = buyeremail;
        this.selleremail = selleremail;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBuyeremail() {
        return buyeremail;
    }

    public void setBuyeremail(String buyeremail) {
        this.buyeremail = buyeremail;
    }

    public String getSelleremail() {
        return selleremail;
    }

    public void setSelleremail(String selleremail) {
        this.selleremail = selleremail;
    }


}
