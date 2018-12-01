package com.smalldata.servicerating.model;

import java.util.Date;

public class RatingLog {

    private String travelId;

    private Date endTime;

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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
