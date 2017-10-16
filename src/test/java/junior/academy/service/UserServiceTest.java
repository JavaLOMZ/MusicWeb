package junior.academy.service;


import junior.academy.dao.UserDao;
import junior.academy.domain.User;

import static org.junit.Assert.*;

import org.mockito.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    UserDao userDao;

    @InjectMocks
    UserService userService;

    @Spy
    List<User> users = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        users = getUserList();
    }

    @Test
    public void createOrUpdateUser() {
        doNothing().when(userDao).createOrUpdateUser((any(User.class)));
        userService.createOrUpdateUser(any(User.class));
        verify(userDao, atLeastOnce()).createOrUpdateUser(any(User.class));
    }

    @Test
    public void getAllUsers() {
        when(userDao.getAllUsers()).thenReturn(users);
        assertEquals(userService.getAllUsers().size(), 1);
    }

    @Test
    public void deleteUserById() {
        doNothing().when(userDao).deleteUserById(anyLong());
        userService.deleteUserById(anyLong());
        verify(userDao, atLeastOnce()).deleteUserById(anyLong());
    }

    @Test
    public void getUserById() {
        User user = users.get(0);
        when(userDao.getUserById(anyLong())).thenReturn(Optional.ofNullable(user));
        assertEquals(userService.getUserById(anyLong()), Optional.of(user));
    }

    @Test
    public void isUserPresent() {
        User user = users.get(0);
        when(userDao.getUserById(anyLong())).thenReturn(Optional.ofNullable(user));
        assertEquals(userService.isUserPresent(user.getUserId()), true);
    }

    public List<User> getUserList() {
        User user = new User();
        user.setNickname("test");
        user.setAdmin(true);
        user.setBanned(false);
        user.setEmail("test@test.com");
        users.add(user);
        return users;
    }
}
