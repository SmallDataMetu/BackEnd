package com.smalldata.servicerating.mapper;

import com.smalldata.servicerating.model.Travel;
import com.smalldata.servicerating.model.TravelEmotionScores;
import com.smalldata.servicerating.request.NewTravelRequest;
import com.smalldata.servicerating.request.SaveEmotionLogRequest;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    private BeanMappingBuilder builder = new BeanMappingBuilder() {
        @Override
        protected void configure() {
            mapping(NewTravelRequest.class, Travel.class)
                    .exclude("createTime")
                    .exclude("startTime")
                    .exclude("travelId");
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
}
