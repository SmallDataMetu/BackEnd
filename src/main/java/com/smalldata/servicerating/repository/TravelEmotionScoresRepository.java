package com.smalldata.servicerating.repository;

import com.smalldata.servicerating.model.TravelEmotionScore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelEmotionScoresRepository extends MongoRepository<TravelEmotionScore, String> {
}
