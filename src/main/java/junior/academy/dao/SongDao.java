package junior.academy.dao;


import junior.academy.domain.Author;
import junior.academy.domain.MusicGenre;
import junior.academy.domain.Song;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EnumType;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class SongDao {

    @Autowired
    private SessionFactory sessionFactory;


    public List<Song> getSongsByAuthorId(long authorId){
        Query query=sessionFactory.getCurrentSession().createQuery("from Song where authorId=:authorId");
        query.setParameter("authorId",authorId);
        return query.list();
    }

    public List<Song> getNotRatedSongs(long userId, MusicGenre musicGenre){
        Query query=sessionFactory.getCurrentSession().createQuery("select song from Rate as rate right join rate.song song with rate.user.userId=:userId where rate.rateValue is null and rate.song.musicGenre=:musicGenre");
        query.setParameter("userId", userId);
        query.setParameter("musicGenre", musicGenre);
        return query.list();
    }

    public List<Song> getRatedSongs(long userId){
        Query query=sessionFactory.getCurrentSession().createQuery("select song from Rate as rate right join rate.song song where rate.user.userId=:userId");
        query.setParameter("userId", userId);
        return query.list();
    }

    public Song getUniqueSongByNameAndAuthor(String songName, long authorId){
        Query query=sessionFactory.getCurrentSession().createQuery("from Song where songName=:songName and authorId=:authorId");
        query.setParameter("songName",songName);
        query.setParameter("authorId",authorId);
        return (Song) query.uniqueResult();
    }

    public List<Song> getSongBySearchWord(String searchWord){
        Query query=sessionFactory.getCurrentSession().createQuery("from Song where songName like :searchWord");
        query.setParameter("searchWord","%"+searchWord+"%");
        return query.list();
    }
}
