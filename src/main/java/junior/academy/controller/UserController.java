package junior.academy.controller;

import junior.academy.domain.User;
import junior.academy.service.UserService;
import junior.academy.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserValidator userValidator;

    @InitBinder()
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(this.userValidator);
    }

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

    @PostMapping("/create")
    public void createOrUpdateUser(@RequestBody @Valid User user) {
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

    @GetMapping("/nick/{nickname}")
    public ResponseEntity<User> findUserByName(@PathVariable String nickname) {
        User responseUser = userService.findUserByName(nickname);
        if (responseUser != null) {
            return new ResponseEntity<>(responseUser, HttpStatus.OK);
        } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email){
        System.out.println(email);
        User responseUser = userService.findUserByEmail(email);
        if (responseUser != null) {
            return new ResponseEntity<>(responseUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}