package com.smalldata.servicerating.controller;

import com.smalldata.servicerating.mapper.RequestMapper;
import com.smalldata.servicerating.model.RatingLog;
import com.smalldata.servicerating.request.GetRatingLogRequest;
import com.smalldata.servicerating.service.DriverTravelService;
import com.smalldata.servicerating.service.RatingLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @ApiOperation(value = "Get Rating Logs", notes = "Given average emotion logs for the vehicleId, driverId, startTime and endTime of the travel. Only provided fields will be used in filter.")
    @PostMapping(value = "/get-rating-logs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RatingLog> getRatingLogs(@RequestBody GetRatingLogRequest getRatingLogRequest) {

        List<RatingLog> ratingLogList = ratingLogService.getRatingLogs(getRatingLogRequest);

        return ratingLogList;
    }
}
