package junior.academy.service;

import junior.academy.dao.DefaultDao;
import junior.academy.dao.UserDao;
import junior.academy.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    DefaultDao defaultDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Optional<User> getUserById(long userId) {
        return userDao.getUserById(userId);
    }

//    public List<User> getAllUsers() {
//        return userDao.getAllUsers();
//    }

    public List<User>getAllUsers(){
        System.out.println(defaultDao.getAll(User.class).size());
        return defaultDao.getAll(User.class);
    }

    public void createOrUpdateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.createOrUpdateUser(user);
    }

    public void deleteUserById(long userId) {
        userDao.deleteUserById(userId);
    }

    public Optional<User> getUserByUsername(String nickname){
        return userDao.getUserByUsername(nickname);
    }

    public Optional<User> getUserByEmail(String email){
        return userDao.getUserByEmail(email);
    }

    public boolean isUserPresent(long userId) {
        return userDao.getUserById(userId).isPresent();
    }

    public boolean isUserPresent(String nickname) {
        return userDao.getUserByUsername(nickname).isPresent();
    }

    public boolean isUserPresentByEmail(String email){
        return userDao.getUserByEmail(email).isPresent();
    }
}
