package junior.academy.dao;

import junior.academy.domain.User;
import org.hibernate.Query;
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

    public Optional<User> getUserById(long userId) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(User.class, userId));

    }

    public void createOrUpdateUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    public void deleteUserById(long userId) {

        sessionFactory.getCurrentSession().delete(getUserById(userId).get());
    }

    public Optional<User> getUserByUsername(String nickname){
        Query query= sessionFactory.getCurrentSession().createQuery("from User where nickname=:nickname");
        query.setParameter("nickname",nickname);
        return Optional.ofNullable((User) query.uniqueResult());
    }

    public Optional<User> getUserByEmail(String email){
        Query query= sessionFactory.getCurrentSession().createQuery("from User where email=:email");
        query.setParameter("email",email);
        return Optional.ofNullable((User) query.uniqueResult());
    }
}
