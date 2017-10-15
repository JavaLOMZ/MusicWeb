package junior.academy.service;

import junior.academy.dao.AuthorDao;
import junior.academy.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    AuthorDao authorDao;

    public Optional<Author> getAuthorById(long authorId){
        return authorDao.getAuthorById(authorId);
    }

    public List<Author> getAllAuthors(){
        return authorDao.getAllAuthors();
    }

    public void createOrUpdateAuthor(Author author){
        authorDao.createOrUpdateAuthor(author);
    }

    public void deleteAuthorById(long authorId){
        authorDao.deleteAuthor(authorId);
    }

    public boolean isAuthorPresent(long authorId){
        return authorDao.getAuthorById(authorId).isPresent();
    }
}
