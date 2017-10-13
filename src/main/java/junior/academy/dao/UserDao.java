package junior.academy.dao;

import junior.academy.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public User getUserById(long userId){
        return sessionFactory.getCurrentSession().get(User.class,userId);
    }

    public List<User> getAllUsers(){
        return sessionFactory.getCurrentSession().createQuery("from User").list();
    }

    public void createOrUpdateUser(User user){
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    public void deleteUser(long userId){
        sessionFactory.getCurrentSession().delete(getUserById(userId));
    }
}
