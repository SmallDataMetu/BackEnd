package com.smalldata.servicerating.repository;

import com.smalldata.servicerating.model.Travel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends MongoRepository<Travel, String> {
}
