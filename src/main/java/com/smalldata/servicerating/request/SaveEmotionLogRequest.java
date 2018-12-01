package com.smalldata.servicerating.request;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SaveEmotionLogRequest {

    @NotNull
    private String travelId;

    private List<EmotionScoreRequest> scores;

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public List<EmotionScoreRequest> getScores() {
        return scores;
    }

    public void setScores(List<EmotionScoreRequest> scores) {
        this.scores = scores;
    }
}
