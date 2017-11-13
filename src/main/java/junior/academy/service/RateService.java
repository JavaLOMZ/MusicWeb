package junior.academy.service;

import junior.academy.dao.RateDao;
import junior.academy.domain.Rate;
import junior.academy.domain.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateService {

    @Autowired
    RateDao rateDao;

    public Optional<Rate> getRateById(long rateId){
        return rateDao.getRateById(rateId);
    }

    public List<Rate> getAllRates(){
        return rateDao.getAllRates();
    }

    public void createOrUpdateRate(Rate rate){
        rateDao.createOrUpdateRate(rate);
    }

    public void deleteRateById(long rateId){
        rateDao.deleteRateById(rateId);
    }

    public boolean isRatePresent(long rateId){
        return rateDao.getRateById(rateId).isPresent();
    }

    public List<Rate> getRatesByUserId(long userId){
        return rateDao.getRatesByUserId(userId);
    }

    public List<Rate> getRatesByUsername(String username) {
        return rateDao.getRatesByUsername(username);
    }

    public List<Rate> getRatesBySongId(long songId){
        return rateDao.getRatesBySongId(songId);
    }

    public double songAverageRate(long songId){
        List<Rate>rates=getRatesBySongId(songId);
        return (rates.stream().mapToDouble(Rate::getRateValue).sum())/rates.size();
    }
}
