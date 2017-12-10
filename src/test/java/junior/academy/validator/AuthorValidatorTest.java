package junior.academy.validator;

import junior.academy.domain.Author;
import junior.academy.service.AuthorService;
import junior.academy.util.ErrorCodes;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorValidatorTest implements ErrorCodes {

    private static final String NAME_VALID = "validname";
    private static final String COUNTRY_VALID = "validcountry";
    private static final String EMPTY_FIELD = "";
    private static final String FIELD_WITH_TOO_MANY_CHARACTERS = StringUtils.repeat("t", 26);
    private static final int YEAR_OF_BIRTH_VALID = 2000;

    private Errors errors;
    private Author author;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorValidator authorValidator;

    @Test
    public void shouldPassValidation() {
        prepareForTest(NAME_VALID, YEAR_OF_BIRTH_VALID, COUNTRY_VALID);
        when(authorService.getAuthorByName(anyString())).thenReturn(null);
        authorValidator.validate(author, errors);
        assertEquals(0, errors.getErrorCount());
    }

    @Test
    public void shouldFailAuthorNameAvailabilityTest() {
        prepareForTest(NAME_VALID, YEAR_OF_BIRTH_VALID, COUNTRY_VALID);
        when(authorService.getAuthorByName(anyString())).thenReturn(Optional.of(new Author()));
        authorValidator.validate(author, errors);
        assertSingleError(NAME_TAKEN);
    }

    @Test
    public void shouldFailEmptyNameTest() {
        prepareForTest(EMPTY_FIELD, YEAR_OF_BIRTH_VALID, COUNTRY_VALID);
        when(authorService.getAuthorByName(anyString())).thenReturn(null);
        authorValidator.validate(author, errors);
        assertSingleError(NOT_ENOUGH_CHARACTERS);
    }

    @Test
    public void shouldFailTooLongNameTest() {
        prepareForTest(FIELD_WITH_TOO_MANY_CHARACTERS, YEAR_OF_BIRTH_VALID, COUNTRY_VALID);
        when(authorService.getAuthorByName(anyString())).thenReturn(null);
        authorValidator.validate(author, errors);
        assertSingleError(TOO_MANY_CHARACTERS);
    }

    @Test
    public void shouldFailTooBigYearTest() {
        final int YEAR_TOO_SMALL = LocalDate.now().getYear() + 1;
        prepareForTest(NAME_VALID, YEAR_TOO_SMALL, COUNTRY_VALID);
        when(authorService.getAuthorByName(anyString())).thenReturn(null);
        authorValidator.validate(author, errors);
        assertSingleError(AUTHOR_TOO_YOUNG);
    }

    @Test
    public void shouldFailEmptyCountryOfOriginTest() {
        prepareForTest(NAME_VALID, YEAR_OF_BIRTH_VALID, EMPTY_FIELD);
        when(authorService.getAuthorByName(anyString())).thenReturn(null);
        authorValidator.validate(author, errors);
        assertSingleError(NOT_ENOUGH_CHARACTERS);
    }

    @Test
    public void shouldFailTooLongCountryOfOriginTest() {
        prepareForTest(FIELD_WITH_TOO_MANY_CHARACTERS, YEAR_OF_BIRTH_VALID, COUNTRY_VALID);
        when(authorService.getAuthorByName(anyString())).thenReturn(null);
        authorValidator.validate(author, errors);
        assertSingleError(TOO_MANY_CHARACTERS);
    }

    private void assertSingleError(String errorCode) {
        assertEquals(1, errors.getErrorCount());
        assertEquals(errorCode, errors.getAllErrors().get(0).getCode());
    }

    private void prepareForTest(String name, int yearOfBirth, String countryOfOrigin) {
        author = new Author(name, yearOfBirth, countryOfOrigin);
        errors = new BeanPropertyBindingResult(author, "author");
    }
}
