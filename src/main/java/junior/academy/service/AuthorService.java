package junior.academy.service;

import junior.academy.dao.AuthorDao;
import junior.academy.dao.DefaultDao;
import junior.academy.domain.Author;
import junior.academy.domain.Rate;
import junior.academy.domain.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
public class AuthorService {

    @Autowired
    AuthorDao authorDao;

    @Autowired
    DefaultDao defaultDao;

    @Autowired
    SongService songService;

    @Autowired
    RateService rateService;

    public Optional<Author>getAuthorById(long authorId){
        getAverageRatesForAllAuthors();
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

    public List<Author> getListToSortElements(String searchWord){
        List<Author>listToSortAuthors;
        if(searchWord!=null && searchWord.compareTo("undefined")!=0 && searchWord.compareTo("null")!=0) listToSortAuthors=getAuthorBySearchWord(searchWord);
        else listToSortAuthors=getAllAuthors();
        return listToSortAuthors;
    }

    public List<Author> getAllAuthorsSortedByName(String searchWord){
        List<Author> authorsSortedByName=getListToSortElements(searchWord);
        authorsSortedByName.sort(Comparator.comparing(Author::getName));
        return authorsSortedByName;
    }

    public List<Author> getAllAuthorsSortedByNameReversed(String searchWord){
        List<Author>authorsSortedByNameReversed=getListToSortElements(searchWord);
        authorsSortedByNameReversed.sort(Comparator.comparing(Author::getName).reversed());
        return authorsSortedByNameReversed;
    }

    public List<Author> getAllAuthorsSortedByYearOfBirth(String searchWord){
        List<Author>authorsSortedYearOfBirth=getListToSortElements(searchWord);
        authorsSortedYearOfBirth.sort(Comparator.comparing(Author::getYearOfBirth));
        return authorsSortedYearOfBirth;
    }

    public List<Author> getAllAuthorsSortedByYearOfBirthReversed(String searchWord){
        List<Author>authorsSortedByYearOfBirthReversed=getListToSortElements(searchWord);
        authorsSortedByYearOfBirthReversed.sort(Comparator.comparing(Author::getYearOfBirth).reversed());
        return authorsSortedByYearOfBirthReversed;
    }

    public List<Author> getAllAuthorsSortedByCountryOfOrigin(String searchWord){
        List<Author>authorsSortedByCountryOfOrigin=getListToSortElements(searchWord);
        authorsSortedByCountryOfOrigin.sort(Comparator.comparing(Author::getCountryOfOrigin));
        return authorsSortedByCountryOfOrigin;
    }

    public List<Author> getAllAuthorsSortedByCountryOfOriginReversed(String searchWord){
        List<Author>authorsSortedByCountryOfOriginReversed=getListToSortElements(searchWord);
        authorsSortedByCountryOfOriginReversed.sort(Comparator.comparing(Author::getCountryOfOrigin).reversed());
        return authorsSortedByCountryOfOriginReversed;
    }

    public double getAverageRateOfAuthorSongs(long authorId){
        List<Song> authorSongs=songService.getSongsByAuthorId(authorId);
        double averageRate= authorSongs.stream().mapToDouble(s->rateService.songAverageRate(s.getSongId())).sum();
        if(averageRate>0) return Math.round(averageRate/authorSongs.size()*100)/100.00;
        return 0.00;
    }

    //todo how to show it in html?
    public Map<Author,Double> getAverageRatesForAllAuthors(){
        return getAllAuthors().stream()
                .collect(Collectors.toMap(a->a, a->getAverageRateOfAuthorSongs(a.getAuthorId())));
    }
}
