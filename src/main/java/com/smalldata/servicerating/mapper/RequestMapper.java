package com.smalldata.servicerating.mapper;

import com.smalldata.servicerating.model.RatingLog;
import com.smalldata.servicerating.model.Travel;
import com.smalldata.servicerating.model.TravelEmotionScores;
import com.smalldata.servicerating.request.GetRatingLogRequest;
import com.smalldata.servicerating.request.NewTravelRequest;
import com.smalldata.servicerating.request.SaveEmotionLogRequest;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMapper {

    private BeanMappingBuilder builder = new BeanMappingBuilder() {
        @Override
        protected void configure() {
            mapping(NewTravelRequest.class, Travel.class)
                    .exclude("createTime")
                    .exclude("startTime")
                    .exclude("travelId");

            mapping(Travel.class, RatingLog.class);

        }
    };

    private DozerBeanMapper mapper = new DozerBeanMapper();

    {
        mapper.addMapping(builder);
    }

    public Travel mapNewTravelRequestToModel(NewTravelRequest newTravelRequest) {
        return mapper.map(newTravelRequest, Travel.class);
    }

    public TravelEmotionScores mapTravelEmotionRequestToModel(SaveEmotionLogRequest saveEmotionLogRequest) {
        return mapper.map(saveEmotionLogRequest, TravelEmotionScores.class);
    }

    public Travel mapGetRatingLogRequestToModel(GetRatingLogRequest getRatingLogRequest) {
        return mapper.map(getRatingLogRequest, Travel.class);
    }

    public RatingLog mapTravelToRatingLog(Travel travel) {
        return mapper.map(travel, RatingLog.class);
    }

    public List<RatingLog> mapTravelListToRatingLogList(List<Travel> travels) {
        List<RatingLog> ratingLogs = new ArrayList<>();
        travels.forEach(travel -> {
            ratingLogs.add(mapTravelToRatingLog(travel));
        });
        return ratingLogs;
    }
}
