package com.smalldata.servicerating.service;

import com.smalldata.servicerating.model.TravelEmotionScore;
import com.smalldata.servicerating.repository.TravelEmotionScoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelEmotionScoreService {

    @Autowired
    private TravelEmotionScoresRepository travelEmotionScoresRepository;

    public void saveTravelEmotionLog(TravelEmotionScore travelEmotionScore) {
        travelEmotionScoresRepository.save(travelEmotionScore);
    }



}
