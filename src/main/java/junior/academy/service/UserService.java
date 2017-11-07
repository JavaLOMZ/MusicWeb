package junior.academy.service;

import junior.academy.dao.UserDao;
import junior.academy.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Optional<User> getUserById(long userId) {
        return userDao.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void createOrUpdateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.createOrUpdateUser(user);
    }

    public void deleteUserById(long userId) {
        userDao.deleteUserById(userId);
    }

    public boolean isUserPresent(long userId) {
        return userDao.getUserById(userId).isPresent();
    }

    public User findUserByName(String nickname){
        return userDao.findUserByName(nickname);
    }
}
