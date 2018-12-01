package com.smalldata.servicerating.controller;

import com.smalldata.servicerating.model.Travel;
import com.smalldata.servicerating.request.NewTravelRequest;
import com.smalldata.servicerating.service.DriverTravelService;
import com.smalldata.backend.utils.CommonUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingApiController {

    private static ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private DriverTravelService driverTravelService;

    @PostMapping(value = "/start-new-travel")
    public Travel startNewTravel(@RequestBody NewTravelRequest newTravelRequest) {

        Travel travel = modelMapper.map(newTravelRequest, Travel.class);

        String travelId = CommonUtils.generateToken();
        travel.setTravelId(travelId);

        // save travel info to db
        Travel travelResponse = driverTravelService.saveDriverTravelInformation(travel);

        return travel;
    }


}
