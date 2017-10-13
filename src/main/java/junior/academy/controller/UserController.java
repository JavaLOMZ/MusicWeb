package junior.academy.controller;

import junior.academy.domain.User;
import junior.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId){
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping()
    public void createOrUpdateUser(@RequestBody User user){
        userService.createOrUpdateUser(user);
    }

    @DeleteMapping()
    public void deleteUser(long userId){
        userService.deleteUserById(userId);
    }
}
