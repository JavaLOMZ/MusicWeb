package junior.academy.dao;

import junior.academy.domain.Author;
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

    public Optional<Author> getAuthorById(long authorId) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Author.class, authorId));
    }

    public List<Author> getAllAuthors() {
        return sessionFactory.getCurrentSession().createQuery("from Author").list();
    }

    public void createOrUpdateAuthor(Author author) {
        sessionFactory.getCurrentSession().saveOrUpdate(author);
    }

    public void deleteAuthor(long authorId) {
        sessionFactory.getCurrentSession().delete(getAuthorById(authorId).get());
    }
}