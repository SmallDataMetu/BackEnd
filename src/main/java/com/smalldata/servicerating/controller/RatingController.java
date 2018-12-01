package com.smalldata.servicerating.controller;

import com.smalldata.servicerating.mapper.RequestMapper;
import com.smalldata.servicerating.model.RatingLog;
import com.smalldata.servicerating.model.Travel;
import com.smalldata.servicerating.request.GetRatingLogRequest;
import com.smalldata.servicerating.service.DriverTravelService;
import com.smalldata.servicerating.service.RatingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RatingController {

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private RatingLogService ratingLogService;

    @Autowired
    private DriverTravelService driverTravelService;

    @PostMapping(value = "/get-rating-logs")
    public List<RatingLog> getRatingLogs(@RequestBody GetRatingLogRequest getRatingLogRequest) {

        List<RatingLog> ratingLogList = ratingLogService.getRatingLogs(getRatingLogRequest);

        return ratingLogList;
    }
}
