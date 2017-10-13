package junior.academy.dao;

import junior.academy.domain.User;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserDaoTest extends EntityDaoTest{

    @Autowired
    UserDao userDao;

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet[] datasets = new IDataSet[] {
                new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User.xml"))
        };
        return new CompositeDataSet(datasets);
    }

    @Test
    public void createUser(){
        userDao.createUser(getUser());
        Assert.assertEquals(userDao.getAllUsers().size(),2);
    }

    @Test
    public void getAllusers(){
        Assert.assertEquals(userDao.getAllUsers().size(),1);
    }

    @Test
    public void getUserById(){
        Assert.assertNotNull(userDao.getUserById(1));
    }

    @Test
    public void deleteUser(){
        userDao.deleteUser(1);
        Assert.assertEquals(userDao.getAllUsers().size(),0);
    }

    @Test
    public void updateUser(){
        userDao.updateUser(getUser());
        Assert.assertEquals(userDao.getUserById(1).getNickname(),"TestUpdate");
    }

    private User getUser(){
        User user=new User();
        user.setUserId(1);
        user.setNickname("TestUpdate");
        user.setAdmin(true);
        user.setBanned(false);
        user.setEmail("test@test.com");
        return user;
    }
}
