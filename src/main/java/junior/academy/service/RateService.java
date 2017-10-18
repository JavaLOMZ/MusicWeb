package junior.academy.service;

import junior.academy.dao.RateDao;
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
}
