package junior.academy.controller;

import junior.academy.domain.User;
import junior.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId) {
        if (userService.isUserPresent(userId)) {
            return new ResponseEntity<>(userService.getUserById(userId).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void createOrUpdateUser(@RequestBody User user) {
        userService.createOrUpdateUser(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUserById(@PathVariable long userId) {
        if (userService.isUserPresent(userId)) {
            userService.deleteUserById(userId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

//    @GetMapping("/nick/{nickname}")
//    public User findUserByName(@PathVariable String nickname) {
//        return userService.findUserByName(nickname);
//    }
}
