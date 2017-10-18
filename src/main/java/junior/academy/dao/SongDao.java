package junior.academy.dao;


import junior.academy.domain.Author;
import junior.academy.domain.Song;
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
}
