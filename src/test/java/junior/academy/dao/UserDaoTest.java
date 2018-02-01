package junior.academy.dao;

import junior.academy.domain.User;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

public class UserDaoTest extends EntityDaoTest {

    @Autowired
    UserDao userDao;

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet[] datasets = new IDataSet[]{
                new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User.xml"))
        };
        return new CompositeDataSet(datasets);
    }

    @Test
    public void getUserByUsername(){
        assertNotNull(userDao.getUserByUsername("Test"));
        assertEquals(userDao.getUserByUsername("Test").getNickname(), "Test");
    }

    @Test
    public void getUserByEmail(){
        assertNotNull(userDao.getUserByEmail("test@test.com"));
        assertEquals(userDao.getUserByEmail("test@test.com").getEmail(), "test@test.com");
    }

    private User getUser() {
        User user = new User();
        user.setNickname("TestUpdate");
        user.setEmail("test@test.com");
        user.setPassword("test");
        return user;
    }
}
