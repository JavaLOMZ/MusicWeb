package junior.academy.service;

import junior.academy.dao.AuthorDao;
import junior.academy.dao.DefaultDao;
import junior.academy.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorService {

    @Autowired
    AuthorDao authorDao;

    @Autowired
    DefaultDao defaultDao;


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

    public boolean isAuthorPresent(String authorName){
        return authorDao.getAuthorByName(authorName).isPresent();
    }

    public Optional<Author> getAuthorByName(String name){
        return authorDao.getAuthorByName(name);
    }

    public List<Author> getAuthorBySearchWord(String searchWord){
        return authorDao.getAuthorBySearchWord(searchWord);
    }

    public List<Author> getAllAuthorsSortedByName(){
        List<Author>authorsSortedByName=defaultDao.getAll(Author.class);
        authorsSortedByName.sort(Comparator.comparing(Author::getName));
        return authorsSortedByName;
    }

    public List<Author> getAllAuthorsSortedByNameReversed(){
        List<Author>authorsSortedByName=defaultDao.getAll(Author.class);
        authorsSortedByName.sort(Comparator.comparing(Author::getName).reversed());
        return authorsSortedByName;
    }

    public List<Author> getAllAuthorsSortedByYearOfBirth(){
        List<Author>authorsSortedByName=defaultDao.getAll(Author.class);
        authorsSortedByName.sort(Comparator.comparing(Author::getYearOfBirth));
        return authorsSortedByName;
    }

    public List<Author> getAllAuthorsSortedByYearOfBirthReversed(){
        List<Author>authorsSortedByName=defaultDao.getAll(Author.class);
        authorsSortedByName.sort(Comparator.comparing(Author::getYearOfBirth).reversed());
        return authorsSortedByName;
    }

    public List<Author> getAllAuthorsSortedByCountryOfOrigin(){
        List<Author>authorsSortedByName=defaultDao.getAll(Author.class);
        authorsSortedByName.sort(Comparator.comparing(Author::getCountryOfOrigin));
        return authorsSortedByName;
    }

    public List<Author> getAllAuthorsSortedByCountryOfOriginReversed(){
        List<Author>authorsSortedByName=defaultDao.getAll(Author.class);
        authorsSortedByName.sort(Comparator.comparing(Author::getCountryOfOrigin).reversed());
        return authorsSortedByName;
    }
}
