//package junior.academy.dao;
//
//import junior.academy.domain.User;
//import org.dbunit.dataset.CompositeDataSet;
//import org.dbunit.dataset.IDataSet;
//import org.dbunit.dataset.xml.FlatXmlDataSet;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.testng.Assert.*;
//
//import org.testng.annotations.Test;
//
//public class UserDaoTest extends EntityDaoTest {
//
//    @Autowired
//    UserDao userDao;
//
//    @Override
//    protected IDataSet getDataSet() throws Exception {
//        IDataSet[] datasets = new IDataSet[]{
//                new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User.xml"))
//        };
//        return new CompositeDataSet(datasets);
//    }
//
//    @Test
//    public void getAllusers() {
//        assertEquals(userDao.getAllUsers().size(), 1);
//    }
//
//    @Test
//    public void getUserById() {
//        assertNotNull(userDao.getUserById(1));
//        assertEquals(userDao.getUserById(1).get().getNickname(), "Test");
//    }
//
//    @Test
//    public void createUser() {
//        User user = getUser();
//        userDao.createOrUpdateUser(user);
//        assertEquals(userDao.getAllUsers().size(), 2);
//    }
//
//
//    @Test
//    public void updateUser() {
//        User user = userDao.getUserById(1).get();
//        user.setNickname("TestingNickname");
//        userDao.createOrUpdateUser(user);
//        assertEquals(userDao.getUserById(1).get().getNickname(), "TestingNickname");
//    }
//
//    @Test
//    public void deleteUser() {
//        userDao.deleteUserById(1);
//        assertEquals(userDao.getAllUsers().size(), 0);
//    }
//
//    private User getUser() {
//        User user = new User();
//        user.setNickname("TestUpdate");
//        user.setIsAdmin(true);
//        user.setIsBanned(false);
//        user.setEmail("test@test.com");
//        user.setPassword("test");
//        return user;
//    }
//}
