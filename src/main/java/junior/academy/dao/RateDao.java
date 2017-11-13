package junior.academy.dao;

import junior.academy.domain.Rate;
import junior.academy.domain.Rate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RateDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<Rate> getRateById(long rateId) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Rate.class, rateId));
    }

    public List<Rate> getAllRates() {
        return sessionFactory.getCurrentSession().createQuery("from Rate").list();
    }

    public void createOrUpdateRate(Rate rate) {
        sessionFactory.getCurrentSession().saveOrUpdate(rate);
    }

    public void deleteRateById(long rateId) {
        sessionFactory.getCurrentSession().delete(getRateById(rateId).get());
    }

    public List<Rate> getRatesByUserId(long userId) {
        Query query=sessionFactory.getCurrentSession().createQuery("from Rate where userId=:userId");
        query.setParameter("userId",userId);
        return query.list();
    }

    public List<Rate> getRatesByUsername(String nickname) {
        Query query=sessionFactory.getCurrentSession().createQuery("select rate from Rate as rate join rate.user user where user.nickname=:nickname");
        query.setParameter("nickname", nickname);
        return query.list();
    }

    public List<Rate> getRatesBySongId(long songId){
        Query query=sessionFactory.getCurrentSession().createQuery("from Rate where songId=:songId");
        query.setParameter("songId",songId);
        return query.list();
    }
}