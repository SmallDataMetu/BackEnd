package com.smalldata.servicerating.controller;

import com.smalldata.servicerating.mapper.RequestMapper;
import com.smalldata.servicerating.model.EmotionScore;
import com.smalldata.servicerating.model.Travel;
import com.smalldata.servicerating.model.TravelEmotionScores;
import com.smalldata.servicerating.request.NewTravelRequest;
import com.smalldata.servicerating.request.SaveEmotionLogRequest;
import com.smalldata.servicerating.service.DriverTravelService;
import com.smalldata.backend.utils.CommonUtils;
import com.smalldata.servicerating.service.TravelEmotionScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RestController
public class RatingApiController {

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private DriverTravelService driverTravelService;

    @Autowired
    private TravelEmotionScoreService travelEmotionScoreService;

    @PostMapping(value = "/start-new-travel")
    public Travel startNewTravel(@RequestBody NewTravelRequest newTravelRequest) {

        Travel travel = requestMapper.mapNewTravelRequestToModel(newTravelRequest);

        String travelId = CommonUtils.generateToken();
        travel.setTravelId(travelId);
        travel.setStartTime(new Date());

        // save travel info to db
        Travel travelResponse = driverTravelService.saveDriverTravelInformation(travel);

        return travel;
    }

    @PostMapping(value = "/save-emotion-log")
    public ResponseEntity<String> saveEmotionLog(@RequestBody SaveEmotionLogRequest saveEmotionLogRequest) {

        if (!driverTravelService.isTravelExist(saveEmotionLogRequest.getTravelId())) {
            return new ResponseEntity<String>("Travel Id Not Exist", HttpStatus.NOT_FOUND);
        }

        TravelEmotionScores emotionScores = requestMapper.mapTravelEmotionRequestToModel(saveEmotionLogRequest);

        travelEmotionScoreService.saveTravelEmotionLog(emotionScores);

        return new ResponseEntity<String>("Emotion Log Saved Successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/complete-travel")
    public ResponseEntity<String> completeTravel(@NotNull @RequestParam String travelId) {

        if (!driverTravelService.isTravelExist(travelId)) {
            return new ResponseEntity<String>("Travel Id Not Exist", HttpStatus.NOT_FOUND);
        }

        Travel travel = driverTravelService.getTravel(travelId);
        travel.setEndTime(new Date());
        driverTravelService.updateTravelById(travel);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }


}
