package com.smalldata.servicerating.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Travel {

    @Id
    private String travelId;

    private String vehicleId;

    private String driverId;

    private Date startTime;

    private Date endTime;

    private EmotionScore rating;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public EmotionScore getRating() {
        return rating;
    }

    public void setRating(EmotionScore rating) {
        this.rating = rating;
    }
}
