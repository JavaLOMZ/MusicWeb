package junior.academy.service;


import junior.academy.dao.AuthorDao;
import junior.academy.dao.DefaultDao;
import junior.academy.domain.Author;

import static org.junit.Assert.*;

import org.mockito.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {
    @Mock
    AuthorDao authorDao;

    @Mock
    DefaultDao defaultDao;

    @InjectMocks
    AuthorService authorService;

    @Spy
    List<Author> authors = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        authors = getAuthorList();
    }

    @Test
    public void createOrUpdateAuthor() {
        doNothing().when(defaultDao).saveOrUpdate((any(Author.class)));
        authorService.createOrUpdateAuthor(any(Author.class));
        verify(defaultDao, atLeastOnce()).saveOrUpdate(any(Author.class));
    }

    @Test
    public void getAllAuthors() {
        when(defaultDao.getAll(Author.class)).thenReturn(authors);
        assertEquals(authorService.getAllAuthors(), authors);
    }

    @Test
    public void deleteAuthorById() {
        long id=authors.get(0).getAuthorId();
        doNothing().when(defaultDao).deleteById(eq(Author.class),anyLong());
        authorService.deleteAuthorById(id);
        verify(defaultDao, atLeastOnce()).deleteById(eq(Author.class),anyLong());
    }

    @Test
    public void getAuthorById() {
        Author author = authors.get(0);
        when(defaultDao.getById(eq(Author.class),anyLong())).thenReturn(Optional.ofNullable(author));
        assertEquals(authorService.getAuthorById(author.getAuthorId()), Optional.of(author));
    }

    @Test
    public void isAuthorPresent(){
        Author author=getAuthorList().get(0);
        when(defaultDao.getById(eq(Author.class),anyLong())).thenReturn(Optional.ofNullable(author));
        assertEquals(authorService.isAuthorPresent(author.getAuthorId()), true);
    }

    @Test
    public void isAuthorPresentByName(){
        Author author=getAuthorList().get(0);
        when(authorDao.getAuthorByName(anyString())).thenReturn(Optional.ofNullable(author));
        assertEquals(authorService.isAuthorPresent(anyString()), true);
    }

    @Test
    public void getAuthorByName(){
        Optional<Author> authorTest = Optional.ofNullable(authors.get(0));
        when(authorDao.getAuthorByName(anyString())).thenReturn(authorTest);
        assertEquals(authorService.getAuthorByName(anyString()), authorTest);
    }

    @Test
    public void getAuthorBySearchWord(){
        List<Author> authorTestList = authors;
        when(authorDao.getAuthorBySearchWord(anyString())).thenReturn(authorTestList);
        assertEquals(authorService.getAuthorBySearchWord(anyString()), authorTestList);
    }

    @Test
    public void getListToSortElementsWithSearchWord(){
        List<Author>listToSortAuthors;
        List<Author>specifiedAuthorList=getAuthorBiggerList();
        String searchWord="bieber";
        when(authorDao.getAuthorBySearchWord(searchWord)).thenReturn(authors);
        when(defaultDao.getAll(eq(Author.class))).thenReturn(specifiedAuthorList);
        if(searchWord!=null && searchWord.compareTo("undefined")!=0 && searchWord.compareTo("null")!=0) listToSortAuthors=authorService.getAuthorBySearchWord(searchWord);
        else listToSortAuthors=authorService.getAllAuthors();
        assertEquals(authorService.getListToSortElements(searchWord).size(),listToSortAuthors.size());
    }

    @Test
    public void getListToSortElementsWithoutSearchWord(){
        List<Author>listToSortAuthors;
        List<Author>specifiedAuthorList=getAuthorBiggerList();
        String searchWord=null;
        when(authorDao.getAuthorBySearchWord(searchWord)).thenReturn(authors);
        when(defaultDao.getAll(eq(Author.class))).thenReturn(specifiedAuthorList);
        if(searchWord!=null && searchWord.compareTo("undefined")!=0 && searchWord.compareTo("null")!=0) listToSortAuthors=authorService.getAuthorBySearchWord(searchWord);
        else listToSortAuthors=authorService.getAllAuthors();
        assertEquals(authorService.getListToSortElements(searchWord).size(),listToSortAuthors.size());
    }

    @Test
    public void getAllAuthorsSortedByName(){
        List<Author> authorsSortedByName=getAuthorBiggerList();
        when(authorDao.getAuthorBySearchWord(anyString())).thenReturn(authorsSortedByName);
        authorsSortedByName.sort(Comparator.comparing(Author::getName));
        assertEquals(authorService.getAllAuthorsSortedByName(anyString()).get(0).getName(),"Aaaa");
    }

    @Test
    public void getAllAuthorsSortedByNameReversed(){
        List<Author> authorsSortedByNameReversed=getAuthorBiggerList();
        when(authorDao.getAuthorBySearchWord(anyString())).thenReturn(authorsSortedByNameReversed);
        authorsSortedByNameReversed.sort(Comparator.comparing(Author::getName).reversed());
        assertEquals(authorService.getAllAuthorsSortedByNameReversed(anyString()).get(0).getName(),"Justin Bieber");
    }

    @Test
    public void getAllAuthorsSortedByYearOfBirth(){
        List<Author> authorsSortedYearOfBirth=getAuthorBiggerList();
        when(authorDao.getAuthorBySearchWord(anyString())).thenReturn(authorsSortedYearOfBirth);
        authorsSortedYearOfBirth.sort(Comparator.comparing(Author::getYearOfBirth));
        assertEquals(authorService.getAllAuthorsSortedByYearOfBirth(anyString()).get(0).getYearOfBirth(),1);
    }

    @Test
    public void getAllAuthorsSortedByYearOfBirthReversed(){
        List<Author> authorsSortedByYearOfBirthReversed=getAuthorBiggerList();
        when(authorDao.getAuthorBySearchWord(anyString())).thenReturn(authorsSortedByYearOfBirthReversed);
        authorsSortedByYearOfBirthReversed.sort(Comparator.comparing(Author::getYearOfBirth).reversed());
        assertEquals(authorService.getAllAuthorsSortedByYearOfBirthReversed(anyString()).get(0).getYearOfBirth(),1994);
    }

    @Test
    public void getAllAuthorsSortedByCountryOfOrigin(){
        List<Author> authorsSortedByCountryOfOrigin=getAuthorBiggerList();
        when(authorDao.getAuthorBySearchWord(anyString())).thenReturn(authorsSortedByCountryOfOrigin);
        authorsSortedByCountryOfOrigin.sort(Comparator.comparing(Author::getCountryOfOrigin));
        assertEquals(authorService.getAllAuthorsSortedByCountryOfOrigin(anyString()).get(0).getCountryOfOrigin(),"AUSA2");
    }

    @Test
    public void getAllAuthorsSortedByCountryOfOriginReversed(){
        List<Author> authorsSortedByCountryOfOriginReversed=getAuthorBiggerList();
        when(authorDao.getAuthorBySearchWord(anyString())).thenReturn(authorsSortedByCountryOfOriginReversed);
        authorsSortedByCountryOfOriginReversed.sort(Comparator.comparing(Author::getCountryOfOrigin).reversed());
        assertEquals(authorService.getAllAuthorsSortedByCountryOfOriginReversed(anyString()).get(0).getCountryOfOrigin(),"USA");
    }

    private List<Author> getAuthorList() {
        Author author = new Author();
        author.setName("Justin Bieber");
        author.setCountryOfOrigin("USA");
        author.setYearOfBirth(1994);
        authors.add(author);
        return authors;
    }

    private List<Author> getAuthorBiggerList() {
        List<Author>authorList=new ArrayList<>();
        Author author = new Author();
        author.setName("Justin Bieber");
        author.setCountryOfOrigin("USA");
        author.setYearOfBirth(1994);

        Author author2 = new Author();
        author2.setName("Aaaa");
        author2.setCountryOfOrigin("AUSA2");
        author2.setYearOfBirth(1);
        authorList.add(author);
        authorList.add(author2);
        return authorList;
    }
}
