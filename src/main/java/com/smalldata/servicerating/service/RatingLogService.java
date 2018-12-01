package com.smalldata.servicerating.service;
import com.smalldata.servicerating.mapper.RequestMapper;
import com.smalldata.servicerating.model.RatingLog;
import com.smalldata.servicerating.model.Travel;
import com.smalldata.servicerating.request.GetRatingLogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.smalldata.backend.utils.CommonUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RatingLogService {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RequestMapper requestMapper;

    public List<RatingLog> getRatingLogs(GetRatingLogRequest getRatingLogRequest) {

        Query query = new Query();
        if (getRatingLogRequest.getDriverId() != null) {
            query.addCriteria(Criteria.where("driverId").is(getRatingLogRequest.getDriverId()));
        }
        if (getRatingLogRequest.getVehicleId() != null) {
            query.addCriteria(Criteria.where("vehicleId").is(getRatingLogRequest.getVehicleId()));
        }
        if (getRatingLogRequest.getStartTime() != null) {
            query.addCriteria(Criteria.where("startTime").gte(CommonUtils.convertTimestampToDate(getRatingLogRequest.getStartTime())));
        }
        if (getRatingLogRequest.getEndTime() != null) {
            query.addCriteria(Criteria.where("driverId").lte(CommonUtils.convertTimestampToDate(getRatingLogRequest.getEndTime())));
        }
        query.addCriteria(Criteria.where("rating").exists(true));

        List<Travel> travels = mongoTemplate.find(query, Travel.class);

        List<RatingLog> ratingLogs = requestMapper.mapTravelListToRatingLogList(travels);

        return ratingLogs;
    }

}
