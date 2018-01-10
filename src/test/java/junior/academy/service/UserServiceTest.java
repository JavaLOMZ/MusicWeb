package junior.academy.service;


import junior.academy.dao.DefaultDao;
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
    DefaultDao defaultDao;

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
        doNothing().when(defaultDao).saveOrUpdate((any(User.class)));
        when(passwordEncoder.encode(user.getPassword())).thenReturn(anyString());
        userService.createOrUpdateUser(user);
        verify(defaultDao, atLeastOnce()).saveOrUpdate(user);
    }

    @Test
    public void getAllUsers() {
        when(defaultDao.getAll(User.class)).thenReturn(users);
        assertEquals(userService.getAllUsers().size(), 1);
    }

    @Test
    public void deleteUserById() {
        long id = users.get(0).getUserId();
        doNothing().when(defaultDao).deleteById(eq(User.class),anyLong());
        userService.deleteUserById(id);
        verify(defaultDao, atLeastOnce()).deleteById(eq(User.class),anyLong());
    }

    @Test
    public void getUserById() {
        User user = users.get(0);
        when(defaultDao.getById(eq(User.class),anyLong())).thenReturn(Optional.ofNullable(user));
        assertEquals(userService.getUserById(user.getUserId()), Optional.of(user));
    }

    @Test
    public void isUserPresent() {
        User user = users.get(0);
        when(defaultDao.getById(eq(User.class),anyLong())).thenReturn(Optional.ofNullable(user));
        assertEquals(userService.isUserPresent(user.getUserId()), true);
    }

    @Test
    public void getUserByName(){
        User user= users.get(0);
        when(userDao.getUserByUsername(anyString())).thenReturn(user);
        assertEquals(userService.getUserByUsername(anyString()),user);
    }

    @Test
    public void getUserByEmail(){
        User user= users.get(0);
        when(userDao.getUserByEmail(anyString())).thenReturn(user);
        assertEquals(userService.getUserByEmail(anyString()), user);
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
