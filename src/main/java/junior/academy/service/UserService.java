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

    public void createUser(User user){
        userDao.createUser(user);
    }

    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }
}
