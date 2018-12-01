package com.smalldata.servicerating.controller;

import com.smalldata.servicerating.model.EmotionScore;
import com.smalldata.servicerating.model.Travel;
import com.smalldata.servicerating.model.TravelEmotionScores;
import com.smalldata.servicerating.request.NewTravelRequest;
import com.smalldata.servicerating.request.SaveEmotionLogRequest;
import com.smalldata.servicerating.service.DriverTravelService;
import com.smalldata.backend.utils.CommonUtils;
import com.smalldata.servicerating.service.TravelEmotionScoreService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RatingApiController {

    private static ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private DriverTravelService driverTravelService;

    @Autowired
    private TravelEmotionScoreService travelEmotionScoreService;

    @PostMapping(value = "/start-new-travel")
    public Travel startNewTravel(@RequestBody NewTravelRequest newTravelRequest) {

        Travel travel = modelMapper.map(newTravelRequest, Travel.class);

        String travelId = CommonUtils.generateToken();
        travel.setTravelId(travelId);

        // save travel info to db
        Travel travelResponse = driverTravelService.saveDriverTravelInformation(travel);

        return travel;
    }

    @PostMapping(value = "/save-emotion-log")
    public ResponseEntity<String> saveEmotionLog(@RequestBody SaveEmotionLogRequest saveEmotionLogRequest) {

        if (!driverTravelService.isTravelExist(saveEmotionLogRequest.getTravelId())) {
            return new ResponseEntity<String>("Travel Id Not Exist", HttpStatus.NOT_FOUND);
        }

        TravelEmotionScores emotionScores = modelMapper.map(saveEmotionLogRequest, TravelEmotionScores.class);

        travelEmotionScoreService.saveTravelEmotionLog(emotionScores);

        return new ResponseEntity<String>("Emotion Log Saved Successfully", HttpStatus.OK);
    }


}
