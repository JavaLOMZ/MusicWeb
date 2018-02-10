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
public class DefaultDaoTest extends ClassPropertiesSetUp {

    private static final String TEST_UPDATED_NICKNAME = "UpdatedNickname";
    private long userId;


    @Autowired
    RateDao rateDao;

    @Before
    public void beforeTest() {
        super.userSetUp();
    }

    @Test
    public void addEntity() {
        final int countBeforeInput = countRowsInTable();
        addOrUpdateUserAndFlushSession();
        assertEquals(countBeforeInput + 1, countRowsInTable());
    }

    @Test
    public void updateEntity(){
        addOrUpdateUserAndFlushSession();
        user.setNickname(TEST_UPDATED_NICKNAME);
        addOrUpdateUserAndFlushSession();
        assertEquals(defaultDao.getById(user.getClass(), userId).get().getNickname(), TEST_UPDATED_NICKNAME);
    }

    @Test
    public void getEntityById(){
        addOrUpdateUserAndFlushSession();
        assertEquals(user, defaultDao.getById(user.getClass(), userId).get());
    }

    @Test
    public void getAll(){
        final int countBeforeInput = countRowsInTable();
        addOrUpdateUserAndFlushSession();
        assertEquals(countBeforeInput+1, defaultDao.getAll(user.getClass()).size());
    }

    @Test
    public void deleteEntity(){
        addOrUpdateUserAndFlushSession();
        final int countBeforeDeletion = countRowsInTable();
        defaultDao.deleteById(user.getClass(), userId);
        assertEquals(countBeforeDeletion-1, countRowsInTable());
    }

    private int countRowsInTable() {
        return sessionFactory.getCurrentSession().createQuery("from User").list().size();
    }

    private void addOrUpdateUserAndFlushSession() {
        defaultDao.saveOrUpdate(user);
        userId = user.getUserId();
        flushCurrentSession();
    }
}
