//package junior.academy.dao;
//
//import junior.academy.domain.User;
//import org.dbunit.dataset.IDataSet;
//import org.dbunit.dataset.xml.FlatXmlDataSet;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.testng.Assert;
//
//public class UserDaoTest extends EntityDaoTest{
//
//    @Autowired
//    UserDao userDao;
//
//    @Override
//    protected IDataSet getDataSet() throws Exception {
//        IDataSet dataSet=new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User.xml"));
//        return dataSet;
//    }
//
////    @Test
////    public void createUser(){
////        userDao.createUser(getUser());
////        Assert.assertEquals(userDao.getAllUsers().size(),2);
////    }
////
////    @Test
////    public void getAllusers(){
////        Assert.assertEquals(userDao.getAllUsers().size(),2);
////    }
//
//    private User getUser(){
//        User user=new User();
//        user.setUserId(2);
//        user.setNickname("Test");
//        user.setAdmin(true);
//        user.setBanned(false);
//        user.setEmail("test@test.com");
//        return user;
//    }
//}
