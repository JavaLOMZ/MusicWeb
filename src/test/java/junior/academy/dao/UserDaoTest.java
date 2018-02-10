package junior.academy.dao;

import junior.academy.config.HibernateTestConfig;
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
public class UserDaoTest extends ClassPropertiesSetUp {

    @Autowired
    UserDao userDao;

    @Before
    public void beforeTest() {
        super.userSetUp();
        addUserToDatabaseAndFlushSession();
    }

    @Test
    public void getUserByNickname() {
        assertTrue(userDao.getUserByNickname(TEST_USER_NICKNAME).isPresent());
    }

    @Test
    public void getUserByEmail() {
        assertTrue(userDao.getUserByEmail(TEST_USER_EMAIL).isPresent());
    }

    private void addUserToDatabaseAndFlushSession() {
        defaultDao.saveOrUpdate(user);
        flushCurrentSession();
    }
}
