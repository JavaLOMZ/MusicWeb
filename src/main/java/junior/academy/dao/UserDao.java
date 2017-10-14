package junior.academy.dao;

import junior.academy.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<User> getUserById(long userId){
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(User.class,userId));

    }

    public Optional<List<User>> getAllUsers(){
        return Optional.ofNullable(sessionFactory.getCurrentSession().createQuery("from User").list());
    }

    public void createOrUpdateUser(User user){
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    public void deleteUser(long userId){

        sessionFactory.getCurrentSession().delete(getUserById(userId).get());
    }
}
