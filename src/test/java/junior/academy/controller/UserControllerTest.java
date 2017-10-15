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
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
public class UserControllerTest {
    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Spy
    List<User> users=new ArrayList<>();

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        users=getUsersList();
    }

    @Test
    public void getUserWhenPresent(){
        User user=getUsersList().get(0);
        boolean answer=userService.getUserById(user.getUserId()).isPresent();
        System.out.println(answer);
        //when(userService.getUserById(anyLong()).isPresent()).thenReturn(true);
        //when(userService.getUserById(anyLong())).thenReturn(Optional.ofNullable(user));
        assertEquals(userController.getUserById(anyLong()),new ResponseEntity<>(user, HttpStatus.OK));
    }

    @Test
    public void getUserWhenNotPresent(){
        //when(userService.getUserById(anyLong()).isPresent()).thenReturn(false);
        assertEquals(userController.getUserById(anyLong()),new ResponseEntity<>(any(User.class),HttpStatus.NOT_FOUND));
    }

    @Test
    public void getAllUsers(){

    }

    @Test
    public void createUser(){

    }

    @Test
    public void deleteUser(){

    }

    public List<User> getUsersList() {
        User user = new User();
        user.setUserId(1);
        user.setNickname("Test");
        user.setAdmin(true);
        user.setBanned(false);
        user.setEmail("test@test.com");
        users.add(user);
        return users;
    }
}
