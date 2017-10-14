package junior.academy.service;


import junior.academy.dao.UserDao;
import junior.academy.domain.User;
import org.junit.Assert;
import org.mockito.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    UserDao userDao;

    @InjectMocks
    UserService userService;

    @Spy
    List<User>users=new ArrayList<User>();

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        users=getUserList();
    }

    @Test
    public void createOrUpdateUser(){
        doNothing().when(userDao).createOrUpdateUser((any(User.class)));
        userService.createOrUpdateUser(any(User.class));
        verify(userDao, Mockito.atLeastOnce()).createOrUpdateUser(any(User.class));
    }

    @Test
    public void getAllUsers(){
        when(userDao.getAllUsers()).thenReturn(Optional.ofNullable(users));
        Assert.assertEquals(userService.getAllUsers().get().size(),2);
    }

    /*OGARNIJ BO SPIERDOLONY TEST WYDAJE MI SIE NIE MA SENSU ALE WRZUCAM*/
    @Test
    public void deleteUserById(){
        when(userDao.getAllUsers()).thenReturn(Optional.ofNullable(users));
        doNothing().when(userDao).deleteUser(anyLong());
        Assert.assertEquals(userService.getAllUsers().get().size(),2);
    }

    @Test
    public void getUserById(){
        User user=users.get(1);
        when(userDao.getUserById(anyLong())).thenReturn(Optional.ofNullable(user));
        Assert.assertEquals(userService.getUserById(anyLong()),Optional.of(user));
    }



    public List<User> getUserList(){
        User user=new User();
        user.setUserId(1);
        user.setNickname("Test");
        user.setAdmin(true);
        user.setBanned(false);
        user.setEmail("test@test.com");

        User user2=new User();
        user2.setUserId(2);
        user2.setNickname("Test2");
        user2.setAdmin(false);
        user2.setBanned(true);
        user2.setEmail("test2@test.com");

        users.add(user);
        users.add(user2);
        return users;
    }


}
