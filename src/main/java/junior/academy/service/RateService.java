package junior.academy.service;

import junior.academy.dao.DefaultDao;
import junior.academy.dao.RateDao;
import junior.academy.domain.Rate;
import junior.academy.domain.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RateService {

    @Autowired
    RateDao rateDao;

    @Autowired
    DefaultDao defaultDao;

    @Autowired
    SongService songService;

    public Optional<Rate> getRateById(long rateId){
        return defaultDao.getById(Rate.class,rateId);
    }

    public List<Rate> getAllRates(){
        return defaultDao.getAll(Rate.class);
    }

    public void createOrUpdateRate(Rate rate){
        defaultDao.saveOrUpdate(rate);
    }

    public void deleteRateById(long rateId){
        defaultDao.deleteById(Rate.class,rateId);
    }

    public boolean isRatePresent(long rateId){
        return defaultDao.getById(Rate.class,rateId).isPresent();
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

    //todo we can delete this method usage from few places now
    public double songAverageRate(long songId){
        List<Rate>rates=getRatesBySongId(songId);
        return Math.round(rates.stream().mapToDouble(Rate::getRateValue).sum()/rates.size()*100)/100.00;
    }

    public Optional<Rate> getRateForUserAndSong(long userId, long songId){
        return rateDao.getRateForUserAndSong(userId,songId);
    }

    public Map<Long, Integer> getSongAndRateValue(long userId){
        List<Rate> rates=getRatesByUserId(userId);
        Map<Long, Integer> mapOfRates=new HashMap<>();
        for(Rate r:rates){
            mapOfRates.put(r.getSong().getSongId(),r.getRateValue());
        }
        return mapOfRates;
    }
}
