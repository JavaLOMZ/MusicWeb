package junior.academy.service;


import junior.academy.dao.UserDao;
import junior.academy.domain.User;

import static org.junit.Assert.*;

import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Mock
    PasswordEncoder passwordEncoder;

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
        User user=users.get(0);
        doNothing().when(userDao).createOrUpdateUser((any(User.class)));
        when(passwordEncoder.encode(user.getPassword())).thenReturn(anyString());
        userService.createOrUpdateUser(user);
        verify(userDao, atLeastOnce()).createOrUpdateUser(user);
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

    @Test
    public void findUserByName(){
        User user=users.get(0);
        when(userDao.findUserByName(user.getNickname())).thenReturn(user);
        assertEquals(userService.findUserByName(user.getNickname()),user);
    }

    @Test
    public void isUserPresentNickname() {
        User user = users.get(0);
        when(userDao.findUserByName(anyString())).thenReturn((user));
        when(userDao.getUserById(anyLong())).thenReturn(Optional.ofNullable(user));
        assertEquals(userService.isUserPresent(user.getNickname()), true);
    }

    public List<User> getUserList() {
        User user = new User();
        user.setNickname("test");
        user.setEmail("test@test.com");
        user.setPassword("test");
        users.add(user);
        return users;
    }
}
