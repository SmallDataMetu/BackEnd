package com.smalldata.servicerating.service;
import com.smalldata.servicerating.mapper.RequestMapper;
import com.smalldata.servicerating.model.EmotionScore;
import com.smalldata.servicerating.model.RatingLog;
import com.smalldata.servicerating.model.Travel;
import com.smalldata.servicerating.model.TravelEmotionScore;
import com.smalldata.servicerating.repository.TravelEmotionScoresRepository;
import com.smalldata.servicerating.repository.TravelRepository;
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
    private TravelEmotionScoresRepository travelEmotionScoresRepository;

    @Autowired
    private DriverTravelService driverTravelService;

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private RequestMapper requestMapper;

    public void finalizeRatingLogs(Travel travel) {

        String travelId = travel.getTravelId();

        Query query = new Query();
        query.addCriteria(Criteria.where("travelId").is(travelId));
        query.addCriteria(Criteria.where("scores").exists(true));

        List<TravelEmotionScore> travelEmotionScores = mongoTemplate.find(query, TravelEmotionScore.class);

        EmotionScore emotionScoreResult = new EmotionScore();

        double anger = 0;
        double contempt = 0;
        double disgust = 0;
        double fear = 0;
        double happiness = 0;
        double neutral = 0;
        double sadness = 0;
        double surprise = 0;
        int count = 0;

        for (TravelEmotionScore travelEmotionScore : travelEmotionScores) {
            for (EmotionScore emotionScore : travelEmotionScore.getScores()) {
                anger += emotionScore.getAnger();
                contempt += emotionScore.getContempt();
                disgust += emotionScore.getDisgust();
                fear += emotionScore.getFear();
                happiness += emotionScore.getHappiness();
                neutral += emotionScore.getNeutral();
                sadness += emotionScore.getSadness();
                surprise += emotionScore.getSurprise();
                count++;
            }
        }

        if (count != 0) {
            emotionScoreResult.setAnger(anger / count);
            emotionScoreResult.setContempt(contempt / count);
            emotionScoreResult.setDisgust(disgust / count);
            emotionScoreResult.setFear(fear / count);
            emotionScoreResult.setHappiness(happiness / count);
            emotionScoreResult.setNeutral(neutral / count);
            emotionScoreResult.setSadness(sadness / count);
            emotionScoreResult.setSurprise(surprise / count);
            travel.setRating(emotionScoreResult);
            travelRepository.save(travel);
        }
    }

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
