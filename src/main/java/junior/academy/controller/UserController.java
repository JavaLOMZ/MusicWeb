package junior.academy.controller;


import junior.academy.domain.User;
import junior.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musicweb")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    public void createUser(){
        User user=new User();
        user.setNickname("Test");
        user.setAdmin(true);
        user.setBanned(false);
        user.setEmail("test@test.com");
        userService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
