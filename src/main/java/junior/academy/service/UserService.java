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
        return defaultDao.getById(User.class,userId);
    }

    public List<User>getAllUsers(){
        return defaultDao.getAll(User.class);
    }

    public void createOrUpdateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        defaultDao.saveOrUpdate(user);
    }

    public void deleteUserById(long userId) {
        defaultDao.deleteById(User.class,userId);
    }

    public User getUserByUsername(String nickname){
        return userDao.getUserByUsername(nickname);
    }

    public User getUserByEmail(String email){
        return userDao.getUserByEmail(email);
    }

    public boolean isUserPresent(long userId) {
        return defaultDao.getById(User.class,userId).isPresent();
    }
}
