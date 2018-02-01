package junior.academy.validator;


import junior.academy.domain.User;
import junior.academy.service.UserService;
import junior.academy.util.ErrorCodes;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest implements ErrorCodes{

    private static String NICKNAME="test";
    private static String PASSWORD="password";
    private static String EMAIL="test@test.pl";

    private Errors errors;
    private User user;

    @Mock
    private UserService userService;

    @Mock
    private EmailValidator emailValidator;

    @InjectMocks
    private UserValidator userValidator;


    //todo testy nie dzialaja bo isPresent nie styka tutaj, moznaby zrobic jak w SongControlelr i metode zmienic zeby zamiast Optional dac Song
    @Test
    public void shouldPassUsernameAvailabilityCheck(){
        prepareForTest(NICKNAME,PASSWORD,EMAIL);
        when(userService.getUserByUsername(anyString())).thenReturn(null);
        when(userService.getUserByEmail(anyString())).thenReturn(null);
        userValidator.validate(user, errors);
        assertEquals(0,errors.getErrorCount());
    }

    @Test
    public void shouldFailUsernameAvailabilityCheck(){
        prepareForTest(NICKNAME,PASSWORD,EMAIL);
        when(userService.getUserByUsername(anyString())).thenReturn((new User()));
        when(userService.getUserByEmail(anyString())).thenReturn(null);
        userValidator.validate(user, errors);
        assertSingleError(USERNAME_TAKEN);
    }

    @Test
    public void shouldFailEmailAvailabilityCheck(){
        String takenEmail="test@test.pl";
        prepareForTest(NICKNAME,PASSWORD,takenEmail);
        when(userService.getUserByUsername(anyString())).thenReturn(null);
        when(userService.getUserByEmail(anyString())).thenReturn((new User()));
        userValidator.validate(user, errors);
        assertSingleError(EMAIL_TAKEN);
    }

    @Test
    public void shouldFailValidationDueToTooShortNickname(){
        String TOO_SHORT_NICKNAME = "te";
        prepareForTest(TOO_SHORT_NICKNAME,PASSWORD,EMAIL);
        when(userService.getUserByUsername(anyString())).thenReturn(null);
        when(userService.getUserByEmail(anyString())).thenReturn(null);
        userValidator.validate(user,errors);
        assertSingleError(NOT_ENOUGH_CHARACTERS);
    }

    @Test
    public void shouldFailValidationDueToTooShortPassword(){
        String TOO_SHORT_PASSWORD = "pass";
        prepareForTest(NICKNAME, TOO_SHORT_PASSWORD,EMAIL);
        when(userService.getUserByUsername(anyString())).thenReturn(null);
        when(userService.getUserByEmail(anyString())).thenReturn(null);
        userValidator.validate(user,errors);
        assertSingleError(NOT_ENOUGH_CHARACTERS);
    }

    @Test
    public void shouldFailValidationDueToTooBigPassword(){
        String TOO_BIG_PASSWORD = "tobigpassword";
        prepareForTest(NICKNAME, TOO_BIG_PASSWORD,EMAIL);
        when(userService.getUserByUsername(anyString())).thenReturn(null);
        when(userService.getUserByEmail(anyString())).thenReturn(null);
        userValidator.validate(user,errors);
        assertSingleError(TOO_MANY_CHARACTERS);
    }

    @Test
    public void shouldFailValidationDueToWrongEmailPattern(){
        String WRONG_EMAIL = "test@test";
        prepareForTest(NICKNAME,PASSWORD, WRONG_EMAIL);
        when(userService.getUserByUsername(anyString())).thenReturn(null);
        when(userService.getUserByEmail(anyString())).thenReturn(null);
        when(emailValidator.isValid(WRONG_EMAIL)).thenReturn(false);
        userValidator.validate(user,errors);
        assertSingleError(WRONG_EMAIL_PATTERN);
    }

    private void assertSingleError(String errorCode){
        assertEquals(1,errors.getErrorCount());
        assertEquals(errorCode,errors.getAllErrors().get(0).getCode());
    }

    private void prepareForTest(String nickname, String password, String email){
        user=new User(nickname,password,email);
        errors=new BeanPropertyBindingResult(user,"user");
    }
}
