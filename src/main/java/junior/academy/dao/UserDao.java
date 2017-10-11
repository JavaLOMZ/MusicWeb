package junior.academy.dao;

import junior.academy.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void createUser(User user){
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    public List<User> getAllUsers(){
        return sessionFactory.getCurrentSession().createQuery("from user").list();
    }
}
