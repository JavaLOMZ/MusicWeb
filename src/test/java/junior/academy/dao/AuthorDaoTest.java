package junior.academy.dao;

import junior.academy.config.HibernateTestConfig;
import junior.academy.domain.Author;
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
public class AuthorDaoTest extends ClassPropertiesSetUp {

    @Autowired
    AuthorDao authorDao;

    @Before
    public void beforeTest() {
        super.authorSetUp();
        addAuthorToDatabaseAndFlushSession();
    }

    @Test
    public void getAuthorByName() {
        assertTrue(authorDao.getAuthorByName(TEST_AUTHOR_NAME).isPresent());
    }

    @Test
    public void getAuthorBySearchWord() {
        assertEquals(author, authorDao.getAuthorBySearchWord(TEST_AUTHOR_SEARCH_WORD).get(0));
    }

    private void addAuthorToDatabaseAndFlushSession() {
        defaultDao.saveOrUpdate(author);
        flushCurrentSession();
    }
}
