package com.smalldata.servicerating.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class TravelEmotionScore {

    private String travelId;

    private List<EmotionScore> scores;

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public List<EmotionScore> getScores() {
        return scores;
    }

    public void setScores(List<EmotionScore> scores) {
        this.scores = scores;
    }
}
