package junior.academy.dao;

import junior.academy.config.HibernateTestConfig;
import junior.academy.domain.Comment;
import junior.academy.domain.Song;
import junior.academy.domain.User;
import org.hibernate.SessionFactory;
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
public class CommentDaoTest extends ClassPropertiesSetUp {

    @Autowired
    CommentDao commentDao;

    private long userId;
    private long songId;

    @Before
    public void beforeTest() {
        super.commentSetUp();
        super.userSetUp();
        song = songSetUp();
        comment.setUser(super.user);
        comment.setSong(super.song);
        addDomainClassesToDatabaseAndFlushSession();
    }

    @Test
    public void getCommentByUserId() {
        assertEquals(comment, commentDao.getCommentsByUserId(userId).get(0));
    }

    @Test
    public void getCommentBySearchWord() {
        assertEquals(comment, commentDao.getCommentsByUserNickname(user.getNickname()).get(0));
    }

    @Test
    public void getCommentsBySongId() {
        assertEquals(comment, commentDao.getCommentsBySongId(songId).get(0));
    }

    @Test
    public void getCommentsByUserAndSongId() {
        assertEquals(comment, commentDao.getCommentByUserIdAndSongId(userId, songId).get());
    }

    private void addDomainClassesToDatabaseAndFlushSession() {
        defaultDao.saveOrUpdate(comment);
        defaultDao.saveOrUpdate(user);
        userId = user.getUserId();
        defaultDao.saveOrUpdate(song);
        songId = song.getSongId();
        super.flushCurrentSession();
    }
}
