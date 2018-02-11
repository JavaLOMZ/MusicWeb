package junior.academy.controller;


import junior.academy.domain.User;
import junior.academy.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Spy
    List<User> users = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        users = getUsersList();
    }

    @Test
    public void getUserWhenPresent() {
        User user = getUsersList().get(0);
        when(userService.isUserPresent(anyLong())).thenReturn(true);
        when(userService.getUserById(anyLong())).thenReturn(Optional.ofNullable(user));
        assertEquals(userController.getUserById(anyLong()), new ResponseEntity<>(user, HttpStatus.OK));
    }

    @Test
    public void getUserWhenNotPresent() {
        when(userService.isUserPresent(anyLong())).thenReturn(false);
        assertEquals(userController.getUserById(anyLong()), new ResponseEntity<>(any(User.class), HttpStatus.NOT_FOUND));
    }

    @Test
    public void getAllUsers() {
        when(userService.getAllUsers()).thenReturn(users);
        assertEquals(userController.getAllUsers().size(), users.size());
    }

    @Test
    public void createUser() {
        doNothing().when(userService).createOrUpdateUser(any(User.class));
        userController.createOrUpdateUser(any(User.class));
        verify(userService, atLeastOnce()).createOrUpdateUser(any(User.class));
    }

    @Test
    public void deleteUserWhenPresent() {
        when(userService.isUserPresent(anyLong())).thenReturn(true);
        doNothing().when(userService).deleteUserById(anyLong());
        assertEquals(userController.deleteUserById(anyLong()), new ResponseEntity(HttpStatus.OK));
        verify(userService, atLeastOnce()).deleteUserById(anyLong());
    }

    @Test
    public void deleteUserWhenNotPresent() {
        when(userService.isUserPresent(anyLong())).thenReturn(false);
        assertEquals(userController.deleteUserById(anyLong()), new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @Test
    public void findUserByUsernameWhenUserExistsTest(){
        User testUser = getUsersList().get(0);
        when(userService.getUserByUsername(anyString())).thenReturn(Optional.ofNullable(testUser));
        assertEquals(userController.getUserByName(anyString()), new ResponseEntity<>(testUser, HttpStatus.OK));
    }

    @Test
    public void findUserByUsernameWhenUserDoesNotExistTest(){
        when(userService.getUserByUsername(anyString())).thenReturn(Optional.empty());
        assertEquals(userController.getUserByName(anyString()), new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Test
    public void findUserByEmailWhenUserExistsTest(){
        User testUser = getUsersList().get(0);
        when(userService.getUserByEmail(anyString())).thenReturn(Optional.ofNullable(testUser));
        assertEquals(userController.getUserByEmail(anyString()), new ResponseEntity<>(testUser, HttpStatus.OK));
    }

    @Test
    public void findUserByEmailWhenUserDoesNotExistTest(){
        when(userService.getUserByEmail(anyString())).thenReturn(Optional.empty());
        assertEquals(userController.getUserByEmail(anyString()), new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public List<User> getUsersList() {
        User user = new User();
        user.setUserId(1);
        user.setNickname("Test");

        user.setEmail("test@test.com");
        user.setPassword("test");
        users.add(user);
        return users;
    }
}
