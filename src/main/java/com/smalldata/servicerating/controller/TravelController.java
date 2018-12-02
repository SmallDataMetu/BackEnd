package com.smalldata.servicerating.controller;

import com.smalldata.servicerating.mapper.RequestMapper;
import com.smalldata.servicerating.model.Travel;
import com.smalldata.servicerating.model.TravelEmotionScore;
import com.smalldata.servicerating.request.NewTravelRequest;
import com.smalldata.servicerating.request.SaveEmotionLogRequest;
import com.smalldata.servicerating.service.DriverTravelService;
import com.smalldata.servicerating.utils.CommonUtils;
import com.smalldata.servicerating.service.RatingLogService;
import com.smalldata.servicerating.service.TravelEmotionScoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@RestController
public class TravelController {

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private DriverTravelService driverTravelService;

    @Autowired
    private TravelEmotionScoreService travelEmotionScoreService;

    @Autowired
    private RatingLogService ratingLogService;

    @ApiOperation(value = "Start New Travel", notes = "Start new travel for given driverId and vehicleId. Api returns the travelId in response")
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

    @ApiOperation(value = "Save Emotion Log", notes = "Save emotions logs of the customers returned from the azure face api for given travelId")
    @PostMapping(value = "/save-emotion-log")
    public ResponseEntity<String> saveEmotionLog(@RequestBody SaveEmotionLogRequest saveEmotionLogRequest) {

        if (!driverTravelService.isTravelExist(saveEmotionLogRequest.getTravelId())) {
            return new ResponseEntity<String>("Travel Id Not Exist", HttpStatus.NOT_FOUND);
        }

        TravelEmotionScore emotionScores = requestMapper.mapTravelEmotionRequestToModel(saveEmotionLogRequest);

        travelEmotionScoreService.saveTravelEmotionLog(emotionScores);

        return new ResponseEntity<String>("Emotion Log Saved Successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Complete Travel", notes = "Calculate and save the average of emotion logs for given travelId")
    @GetMapping(value = "/complete-travel")
    public ResponseEntity<String> completeTravel(@NotNull @RequestParam String travelId) {

        if (!driverTravelService.isTravelExist(travelId)) {
            return new ResponseEntity<String>("Travel Id Not Exist", HttpStatus.NOT_FOUND);
        }

        Travel travel = driverTravelService.getTravel(travelId);
        travel.setEndTime(new Date());

        ratingLogService.finalizeRatingLogs(travel);

        driverTravelService.updateTravelById(travel);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }


}
