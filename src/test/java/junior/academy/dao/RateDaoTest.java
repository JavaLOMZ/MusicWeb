package junior.academy.dao;

import junior.academy.config.HibernateTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HibernateTestConfig.class})
@Transactional
public class RateDaoTest extends ClassPropertiesSetUp {

    private long songId;
    private long userId;

    @Autowired
    RateDao rateDao;

    @Before
    public void beforeTest() {
        super.rateSetUp();
        super.userSetUp();
        song = songSetUp();
        rate.setUser(super.user);
        rate.setSong(super.song);
        addRateToDatabaseAndFlushSession();
    }

    @Test
    public void getRatesByUserId() {
        assertEquals(rate, rateDao.getRatesByUserId(userId).get(0));
    }

    @Test
    public void getRatesByUserNickname() {
        assertEquals(rate, rateDao.getRatesByNickname(user.getNickname()).get(0));
    }

    @Test
    public void getRatesBySongId() {
        assertEquals(rate, rateDao.getRatesBySongId(songId).get(0));
    }

    @Test
    public void getRateByUserAndSongId(){
        assertTrue(rateDao.getRateForUserAndSong(userId, songId).isPresent());
    }

    private void addRateToDatabaseAndFlushSession() {
        defaultDao.saveOrUpdate(rate);
        defaultDao.saveOrUpdate(user);
        userId = user.getUserId();
        defaultDao.saveOrUpdate(song);
        songId = song.getSongId();
        flushCurrentSession();
    }
}
