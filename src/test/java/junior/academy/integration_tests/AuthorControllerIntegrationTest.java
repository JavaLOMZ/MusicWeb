package junior.academy.integration_tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import junior.academy.config.Application;
import junior.academy.controller.AuthorController;
import junior.academy.domain.Author;
import junior.academy.domain.Song;
import junior.academy.security.JwtAuthenticationRequest;
import junior.academy.service.AuthorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@WebMvcTest(value = AuthorController.class)
public class AuthorControllerIntegrationTest {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String INVALID_USERNAME = "username";
    private static final String INVALID_PASSWORD = "password";

    private static final Long ID = 1L;
    private static final Long ID_2 = 2L;
    private static final String NAME = "testName";
    private static final String NAME_2 ="zTestName";
    private static final Integer YEAR_OF_BIRTH = 1;
    private static final String COUNTRY_OF_ORIGIN = "testCountry";
    private static final Set<Song> SONGS = new HashSet<>();
    private static final Double AVERAGE_RATE = 1.01;

    private static final String SEARCH_WORD = "z";
    private static final String BLANK_SEARCH_WORD = " ";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturnUnauthorizedStatusBecauseOfLackOfLoginParameters() throws Exception {
        mockMvc.perform(get("/author"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorizedStatusBecauseOfWrongUsername() throws Exception {
        mockMvc.perform(get("/author").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getAuthenticationToken(INVALID_USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorizedStatusBecauseOfWrongPassword() throws Exception {
        mockMvc.perform(get("/author").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, INVALID_PASSWORD)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorizedStatusBecauseOfWrongUsernameAndPassword() throws Exception {
        mockMvc.perform(get("/author").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getAuthenticationToken(INVALID_USERNAME, INVALID_PASSWORD)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnOkStatusBecauseOfMatchingLoginParameters() throws Exception {
        mockMvc.perform(get("/author").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotReturnAuthorByIdBecauseOfMismatchedId() throws Exception {
        Optional<Author> author = Optional.of(createAuthor(ID_2, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));

        when(authorService.isAuthorPresent(author.get().getAuthorId())).thenReturn(false);
        when(authorService.getAuthorById(author.get().getAuthorId())).thenReturn(author);

        mockMvc.perform(get("/author/{id}",ID).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnAuthorById() throws Exception {
        Optional<Author> author = Optional.of(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));

        when(authorService.isAuthorPresent(author.get().getAuthorId())).thenReturn(true);
        when(authorService.getAuthorById(author.get().getAuthorId())).thenReturn(author);

        mockMvc.perform(get("/author/{id}",ID).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.yearOfBirth").value(YEAR_OF_BIRTH))
                .andExpect(jsonPath("$.countryOfOrigin").value(COUNTRY_OF_ORIGIN))
                .andExpect(jsonPath("$.songs.size()").value(SONGS.size()))
                .andExpect(jsonPath("$.authorAverageRate").value(AVERAGE_RATE));
    }

    @Test
    public void shouldReturnAllAuthors() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));
        when(authorService.getAllAuthors()).thenReturn(authors);
        mockMvc.perform(get("/author").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].authorId").value(ID))
                .andExpect(jsonPath("$.[0].name").value(NAME))
                .andExpect(jsonPath("$.[0].yearOfBirth").value(YEAR_OF_BIRTH))
                .andExpect(jsonPath("$.[0].countryOfOrigin").value(COUNTRY_OF_ORIGIN))
                .andExpect(jsonPath("$.[0].songs.size()").value(SONGS.size()))
                .andExpect(jsonPath("$.[0].authorAverageRate").value(AVERAGE_RATE));
    }

    @Test
    public void shouldNotCreateAuthorBecauseOfTakenName() throws Exception{
        Author author = createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, null, AVERAGE_RATE);
        when(authorService.getAuthorByName(author.getName())).thenReturn(Optional.of(author));
        doNothing().when(authorService).createOrUpdateAuthor(author);
        mockMvc.perform(post("/author").content(json(createAuthor(ID ,NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE))).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreateAuthor() throws Exception{
        Author author = createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, null, AVERAGE_RATE);
        when(authorService.getAuthorByName(author.getName())).thenReturn(Optional.empty());
        doNothing().when(authorService).createOrUpdateAuthor(author);
        mockMvc.perform(post("/author").content(json(createAuthor(ID ,NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE))).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotDeleteAuthorBecauseOfMismatchedId() throws Exception {
        Optional<Author> author = Optional.of(createAuthor(ID_2, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));

        when(authorService.isAuthorPresent(author.get().getAuthorId())).thenReturn(false);
        doNothing().when(authorService).deleteAuthorById(author.get().getAuthorId());

        mockMvc.perform(delete("/author/{id}",ID).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteAuthorById() throws Exception {
        Optional<Author> author = Optional.of(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));

        when(authorService.isAuthorPresent(author.get().getAuthorId())).thenReturn(true);
        doNothing().when(authorService).deleteAuthorById(author.get().getAuthorId());

        mockMvc.perform(delete("/author/{id}",ID).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotReturnUniqueAuthorByName() throws Exception {
        Optional<Author> author = Optional.of(createAuthor(ID, NAME_2, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));

        when(authorService.isAuthorPresent(author.get().getName())).thenReturn(false);
        when(authorService.getAuthorByName(author.get().getName())).thenReturn(author);

        mockMvc.perform(get("/author/name/{name}",NAME).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnUniqueAuthorByName() throws Exception {
        Optional<Author> author = Optional.of(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));

        when(authorService.isAuthorPresent(author.get().getName())).thenReturn(true);
        when(authorService.getAuthorByName(author.get().getName())).thenReturn(author);

        mockMvc.perform(get("/author/name/{name}",NAME).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.yearOfBirth").value(YEAR_OF_BIRTH))
                .andExpect(jsonPath("$.countryOfOrigin").value(COUNTRY_OF_ORIGIN))
                .andExpect(jsonPath("$.songs.size()").value(SONGS.size()))
                .andExpect(jsonPath("$.authorAverageRate").value(AVERAGE_RATE));
    }

    @Test
    public void shouldReturnAuthorBySearchWord() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, null, AVERAGE_RATE));
        when(authorService.getAuthorBySearchWord(SEARCH_WORD)).thenReturn(authors);

        mockMvc.perform(get("/author/search/{searchWord}",SEARCH_WORD).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorId").value(ID))
                .andExpect(jsonPath("$.[0].name").value(NAME))
                .andExpect(jsonPath("$.[0].yearOfBirth").value(YEAR_OF_BIRTH))
                .andExpect(jsonPath("$.[0].countryOfOrigin").value(COUNTRY_OF_ORIGIN))
                .andExpect(jsonPath("$.[0].authorAverageRate").value(AVERAGE_RATE));
    }

    @Test
    public void shouldReturnAuthorsSortedByName() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));
        when(authorService.getAllAuthorsSortedByName(BLANK_SEARCH_WORD)).thenReturn(authors);

        mockMvc.perform(get("/author/sortedByName/{searchWord}",BLANK_SEARCH_WORD).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorId").value(ID));
    }

    @Test
    public void shouldReturnAuthorsSortedByNameReversed() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));
        when(authorService.getAllAuthorsSortedByNameReversed(BLANK_SEARCH_WORD)).thenReturn(authors);

        mockMvc.perform(get("/author/sortedByNameReversed/{searchWord}",BLANK_SEARCH_WORD).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorId").value(ID));
    }

    @Test
    public void shouldReturnAuthorsSortedByYearOfBirth() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));
        when(authorService.getAllAuthorsSortedByYearOfBirth(BLANK_SEARCH_WORD)).thenReturn(authors);

        mockMvc.perform(get("/author/sortedByYearOfBirth/{searchWord}",BLANK_SEARCH_WORD).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorId").value(ID));
    }

    @Test
    public void shouldReturnAuthorsSortedByYearOfBirthReversed() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));
        when(authorService.getAllAuthorsSortedByYearOfBirthReversed(BLANK_SEARCH_WORD)).thenReturn(authors);

        mockMvc.perform(get("/author/sortedByYearOfBirthReversed/{searchWord}",BLANK_SEARCH_WORD).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorId").value(ID));
    }

    @Test
    public void shouldReturnAuthorsSortedByCountryOfOrigin() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));
        when(authorService.getAllAuthorsSortedByCountryOfOrigin(BLANK_SEARCH_WORD)).thenReturn(authors);

        mockMvc.perform(get("/author/sortedByCountryOfOrigin/{searchWord}",BLANK_SEARCH_WORD).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorId").value(ID));
    }

    @Test
    public void shouldReturnAuthorsSortedByCountryOfOriginReversed() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));
        when(authorService.getAllAuthorsSortedByCountryOfOriginReversed(BLANK_SEARCH_WORD)).thenReturn(authors);

        mockMvc.perform(get("/author/sortedByCountryOfOriginReversed/{searchWord}",BLANK_SEARCH_WORD).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorId").value(ID));
    }

    @Test
    public void shouldReturnAuthorsSortedByAverageRate() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));
        when(authorService.getAllAuthorsSortedByAverageRate(BLANK_SEARCH_WORD)).thenReturn(authors);

        mockMvc.perform(get("/author/sortedByAverageRate/{searchWord}",BLANK_SEARCH_WORD).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorId").value(ID));
    }

    @Test
    public void shouldReturnAuthorsSortedByAverageRateReversed() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(createAuthor(ID, NAME, YEAR_OF_BIRTH, COUNTRY_OF_ORIGIN, SONGS, AVERAGE_RATE));
        when(authorService.getAllAuthorsSortedByAverageRateReversed(BLANK_SEARCH_WORD)).thenReturn(authors);

        mockMvc.perform(get("/author/sortedByAverageRateReversed/{searchWord}",BLANK_SEARCH_WORD).header("Authorization", "Bearer " + getAuthenticationToken(USERNAME, PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].authorId").value(ID));
    }



    private String getAuthenticationToken(String username, String password) throws Exception {
        JwtAuthenticationRequest jwtAuthenticationRequest = new JwtAuthenticationRequest(username, password);
        MvcResult response = mockMvc.perform(post("/auth")
                .content(json(jwtAuthenticationRequest))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        return conversionResponseIntoToken(response.getResponse().getContentAsString());
    }

    private String json(Object o) throws IOException {
        return mapper.writeValueAsString(o);
    }

    private String conversionResponseIntoToken(String token) throws UnsupportedEncodingException {
        if (token.length() == 0) {
            return "";
        }
        return token
                .replaceAll("[{:}]", "")
                .replaceAll(" ", "")
                .replaceAll("\"", "")
                .substring(5);
    }


    private Author createAuthor(Long id, String name, Integer yearOfBirth, String countryOfOrigin, Set<Song> songs, Double averageRate) {
        Author author = new Author();
        author.setAuthorId(id);
        author.setName(name);
        author.setYearOfBirth(yearOfBirth);
        author.setCountryOfOrigin(countryOfOrigin);
        author.setAuthorAverageRate(averageRate);
        author.setSongs(songs);
        return author;
    }

}
