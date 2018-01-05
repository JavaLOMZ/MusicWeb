package junior.academy.dao;

import junior.academy.domain.Author;
import junior.academy.domain.Song;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AuthorDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<Author> getAuthorByName(String name){
        Query query= sessionFactory.getCurrentSession().createQuery("from Author where name=:name");
        query.setParameter("name",name);
        return Optional.ofNullable((Author) query.uniqueResult());
    }

    public List<Author> getAuthorBySearchWord(String searchWord){
        Query query=sessionFactory.getCurrentSession().createQuery("from Author where name like :searchWord");
        query.setParameter("searchWord","%"+searchWord+"%");
        return query.list();
    }
}