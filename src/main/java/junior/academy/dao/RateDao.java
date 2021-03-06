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

    public List<Rate> getRatesByUserId(long userId) {
        Query query=sessionFactory.getCurrentSession().createQuery("from Rate where userId=:userId");
        query.setParameter("userId",userId);
        return query.list();
    }

    public List<Rate> getRatesByNickname(String nickname) {
        Query query=sessionFactory.getCurrentSession().createQuery("select rate from Rate as rate join rate.user user where user.nickname=:nickname");
        query.setParameter("nickname", nickname);
        return query.list();
    }

    public List<Rate> getRatesBySongId(long songId){
        Query query=sessionFactory.getCurrentSession().createQuery("from Rate where songId=:songId");
        query.setParameter("songId",songId);
        return query.list();
    }

    public Optional<Rate> getRateForUserAndSong(long userId, long songId){
        Query query=sessionFactory.getCurrentSession().createQuery("from Rate where userId=:userId and songId=:songId");
        query.setParameter("userId",userId);
        query.setParameter("songId",songId);
        return Optional.ofNullable((Rate)query.uniqueResult());
    }
}