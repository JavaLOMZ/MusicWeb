package junior.academy.service;

import junior.academy.dao.AuthorDao;
import junior.academy.dao.DefaultDao;
import junior.academy.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    AuthorDao authorDao;

    @Autowired
    DefaultDao defaultDao;

//    public Optional<Author> getAuthorById(long authorId){
//        return authorDao.getAuthorById(authorId);
//    }

    public Optional<Author>getAuthorById(long authorId){
        return defaultDao.getById(Author.class,authorId);
    }

    public List<Author> getAllAuthors(){
        return defaultDao.getAll(Author.class);
    }

    public void createOrUpdateAuthor(Author author){
        defaultDao.saveOrUpdate(author);
    }

    public void deleteAuthorById(long authorId){
        defaultDao.deleteById(Author.class,authorId);
    }
    public boolean isAuthorPresent(long authorId){
        return defaultDao.getById(Author.class,authorId).isPresent();
    }

//    public List<Author> getAllAuthors(){
//        return authorDao.getAllAuthors();
//    }

//    public void createOrUpdateAuthor(Author author){
//        authorDao.createOrUpdateAuthor(author);
//    }

//    public void deleteAuthorById(long authorId){
//        authorDao.deleteAuthorById(authorId);
//    }

//    public boolean isAuthorPresent(long authorId){
//        return authorDao.getAuthorById(authorId).isPresent();
//    }

    public boolean isAuthorPresent(String authorName){
        return authorDao.getAuthorByName(authorName).isPresent();
    }

    public Optional<Author> getAuthorByName(String name){
        return authorDao.getAuthorByName(name);
    }

    public List<Author> getAuthorBySearchWord(String searchWord){
        return authorDao.getAuthorBySearchWord(searchWord);
    }
}
