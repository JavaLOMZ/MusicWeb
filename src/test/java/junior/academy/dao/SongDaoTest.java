package junior.academy.dao;

import junior.academy.config.HibernateTestConfig;
import junior.academy.domain.MusicGenre;
import junior.academy.domain.Song;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HibernateTestConfig.class})
@Transactional
public class SongDaoTest extends ClassPropertiesSetUp {

    private static final String TEST_SONG_SEARCH_WORD_NOT_FINDABLE = "NotFindableSearchWord";
    private long authorId;
    private long userId;
    private Song songNotRated;

    @Autowired
    SongDao songDao;

    @Before
    public void beforeTest() {
        song = songSetUp();
        super.authorSetUp();
        super.userSetUp();
        super.rateSetUp();
        song.setAuthor(author);
        rate.setUser(user);
        rate.setSong(song);
        addSongToDatabaseAndFlushSession();

    }

    @Test
    public void getUsersNotRatedSongs() {
        assertEquals(song, songDao.getSongsByAuthorId(authorId).get(0));
    }

    @Test
    public void getSongNotRatedWithSameGenre(){
        addAnotherSong();
        assertEquals(songNotRated, songDao.getNotRatedSongs(userId, song.getMusicGenre()).get(0));
    }

    @Test
    public void getRatedSongs(){
        addAnotherSong();
        assertEquals(song, songDao.getRatedSongs(userId).get(0));
    }

    @Test
    public void getUniqueSongByNameAndAuthorId(){
        assertEquals(song, songDao.getUniqueSongByNameAndAuthor(TEST_SONG_NAME, authorId));
    }

    @Test
    public void getSongsBySearchWord(){
        assertEquals(song, songDao.getSongBySearchWord(TEST_SONG_SEARCH_WORD).get(0));
    }

    @Test
    public void shouldReturnEmptyListBySearch(){
        assertEquals(0, songDao.getSongBySearchWord(TEST_SONG_SEARCH_WORD_NOT_FINDABLE).size());
    }

    private void addSongToDatabaseAndFlushSession() {
        defaultDao.saveOrUpdate(song);
        defaultDao.saveOrUpdate(author);
        defaultDao.saveOrUpdate(user);
        defaultDao.saveOrUpdate(rate);
        authorId = author.getAuthorId();
        userId = user.getUserId();
        flushCurrentSession();
    }

    private void addAnotherSong(){
        songNotRated = songSetUp();
        defaultDao.saveOrUpdate(songNotRated);
        flushCurrentSession();
    }
}
