package junior.academy.service;

import junior.academy.dao.UserDao;
import junior.academy.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getUserById(long userId){
        return userDao.getUserById(userId);
    }

    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }

    public void createOrUpdateUser(User user){
        userDao.createOrUpdateUser(user);
    }

    public void deleteUserById(long userId){
        userDao.deleteUser(userId);
    }
}
