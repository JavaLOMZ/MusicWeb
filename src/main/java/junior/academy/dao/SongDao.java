package junior.academy.dao;


import junior.academy.domain.Author;
import junior.academy.domain.Song;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class SongDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<Song> getSongById(long songId){
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Song.class,songId));

    }

    public List<Song> getAllSongs(){
        return sessionFactory.getCurrentSession().createQuery("from Song").list();
    }

    public void createOrUpdateSong(Song song){
        sessionFactory.getCurrentSession().saveOrUpdate(song);
    }

    public void deleteSongById(long songId){
        sessionFactory.getCurrentSession().delete(getSongById(songId).get());
    }

    public List<Song> getSongsByAuthorId(long authorId){
        Query query=sessionFactory.getCurrentSession().createQuery("from Song where authorId=:authorId");
        query.setParameter("authorId",authorId);
        return query.list();
    }

    public List<Song> getNotRatedSongsByUserId(long userId){
        Query query=sessionFactory.getCurrentSession().createQuery("select song from Rate as rate right join rate.song song with rate.user.userId=:userId where rate.rateValue is null");
        query.setParameter("userId", userId);
        return query.list();
    }

    public List<Song> getRatedSongsByUserId(long userId){
        Query query=sessionFactory.getCurrentSession().createQuery("select song from Rate as rate right join rate.song song where rate.user.userId=:userId");
        query.setParameter("userId", userId);
        return query.list();
    }
}
