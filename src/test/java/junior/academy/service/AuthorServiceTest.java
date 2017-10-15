package junior.academy.service;


import junior.academy.dao.AuthorDao;
import junior.academy.domain.Author;

import static org.junit.Assert.*;

import org.mockito.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {
    @Mock
    AuthorDao authorDao;

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
        doNothing().when(authorDao).createOrUpdateAuthor((any(Author.class)));
        authorService.createOrUpdateAuthor(any(Author.class));
        verify(authorDao, atLeastOnce()).createOrUpdateAuthor(any(Author.class));
    }

    @Test
    public void getAllAuthors() {
        when(authorDao.getAllAuthors()).thenReturn(authors);
        assertEquals(authorService.getAllAuthors(), authors);
    }

    @Test
    public void deleteAuthorById() {
        doNothing().when(authorDao).deleteAuthorById(anyLong());
        authorService.deleteAuthorById(anyLong());
        verify(authorDao, atLeastOnce()).deleteAuthorById(anyLong());
    }

    @Test
    public void getAuthorById() {
        Author author = authors.get(0);
        when(authorDao.getAuthorById(anyLong())).thenReturn(Optional.ofNullable(author));
        assertEquals(authorService.getAuthorById(anyLong()), Optional.of(author));
    }

    private List<Author> getAuthorList() {
        Author author = new Author();
        author.setName("Justin Bieber");
        author.setCountryOfOrigin("USA");
        author.setYearOfBirth(1994);

        authors.add(author);
        return authors;
    }
}
