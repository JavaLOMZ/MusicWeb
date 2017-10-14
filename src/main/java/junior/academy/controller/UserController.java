package junior.academy.controller;

import junior.academy.domain.User;
import junior.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId){
        if(userService.getUserById(userId).isPresent()) {
            return new ResponseEntity<>(userService.getUserById(userId).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        if (userService.getAllUsers().isPresent()){
            return new ResponseEntity<List<User>>(userService.getAllUsers().get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping()
    public void createOrUpdateUser(@RequestBody User user){
        userService.createOrUpdateUser(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable  long userId){
        if(userService.getUserById(userId).isPresent()){
            userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
