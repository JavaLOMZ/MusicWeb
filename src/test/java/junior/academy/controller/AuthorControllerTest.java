//package junior.academy.controller;
//
//import junior.academy.controller.AuthorController;
//import junior.academy.domain.Author;
//import junior.academy.service.AuthorService;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.Spy;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.*;
//
//public class AuthorControllerTest {
//
//    @Mock
//    AuthorService authorService;
//
//    @InjectMocks
//    AuthorController authorController;
//
//    @Spy
//    List<Author> authors = new ArrayList<>();
//
//    @BeforeClass
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        authors = getAuthorList();
//    }
//
//    @Test
//    public void getAuthorWhenPresent(){
//        Author testAuthor = authors.get(0);
//        when(authorService.isAuthorPresent(anyLong())).thenReturn(true);
//        when(authorService.getAuthorById(anyLong())).thenReturn(java.util.Optional.ofNullable(testAuthor));
//        assertEquals(authorController.getAuthorById(anyLong()), new ResponseEntity<>(testAuthor, HttpStatus.OK));
//    }
//
//    @Test
//    public void getAuthorWhenNotPresent(){
//        when(authorService.isAuthorPresent(anyLong())).thenReturn(false);
//        assertEquals(authorController.getAuthorById(anyLong()), new ResponseEntity<>(any(Author.class), HttpStatus.NOT_FOUND));
//    }
//
//    @Test
//    public void getAllAuthors() {
//        when(authorService.getAllAuthors()).thenReturn(authors);
//        assertEquals(authorController.getAllAuthors(), authors);
//    }
//
//    @Test
//    public void createOrUpdateAuthor(){
//        doNothing().when(authorService).createOrUpdateAuthor((any(Author.class)));
//        authorController.createOrUpdateAuthor(any(Author.class));
//        verify(authorService, atLeastOnce()).createOrUpdateAuthor(any(Author.class));
//    }
//
//    @Test
//    public void deleteAuthorWhenPresent() {
//        when(authorService.isAuthorPresent(anyLong())).thenReturn(true);
//        doNothing().when(authorService).deleteAuthorById(anyLong());
//        assertEquals(authorController.deleteAuthorById(anyLong()), new ResponseEntity(HttpStatus.OK));
//        verify(authorService, atLeastOnce()).deleteAuthorById(anyLong());
//    }
//
//    @Test
//    public void deleteAuthorWhenNotPresent() {
//        when(authorService.isAuthorPresent(anyLong())).thenReturn(false);
//        assertEquals(authorController.deleteAuthorById(anyLong()), new ResponseEntity(HttpStatus.NOT_FOUND));
//    }
//
//    private List<Author> getAuthorList() {
//        Author author = new Author();
//        author.setName("Justin Bieber");
//        author.setCountryOfOrigin("USA");
//        author.setYearOfBirth(1994);
//
//        authors.add(author);
//        return authors;
//    }
//}
