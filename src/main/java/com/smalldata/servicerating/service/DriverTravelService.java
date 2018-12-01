package com.smalldata.servicerating.service;

import com.smalldata.servicerating.model.Travel;
import com.smalldata.servicerating.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverTravelService {

    @Autowired
    TravelRepository travelRepository;

    public Travel saveDriverTravelInformation(Travel travel) {
        return travelRepository.save(travel);
    }

    public boolean isTravelExist(String id) {
        return travelRepository.existsById(id);
    }
}
