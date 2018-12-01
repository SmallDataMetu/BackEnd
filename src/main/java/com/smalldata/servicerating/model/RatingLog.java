package com.smalldata.servicerating.model;

public class RatingLog {

    private String travelId;

    private EmotionScore rating;

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public EmotionScore getRating() {
        return rating;
    }

    public void setRating(EmotionScore rating) {
        this.rating = rating;
    }
}
