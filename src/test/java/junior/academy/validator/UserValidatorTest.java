package junior.academy.validator;


import junior.academy.domain.User;
import junior.academy.util.ErrorCodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest implements ErrorCodes{

    private static String NICKNAME="test";
    private static String TOO_SHORT_NICKNAME="te";
    private static String PASSWORD="password";
    private static String TOO_SHORT_PASSWORD="pass";
    private static String TOO_BIG_PASSWORD="tobigpassword";
    private static String EMAIL="test@test.pl";
    private static String WRONG_EMAIL="test@test";



    private Errors errors;
    private User user;

    @InjectMocks
    private UserValidator userValidator;

    @Test
    public void shouldPassValidation(){
        prepareForTest(NICKNAME,PASSWORD,EMAIL);
        userValidator.validate(user,errors);
        assertEquals(0,errors.getErrorCount());
    }

    @Test
    public void shouldFailValidationDueToTooShortNickname(){
        prepareForTest(TOO_SHORT_NICKNAME,PASSWORD,EMAIL);
        userValidator.validate(user,errors);
        assertSingleError(NOT_ENOUGH_CHARACTERS);
    }

    @Test
    public void shouldFailValidationDueToTooShortPassword(){
        prepareForTest(NICKNAME,TOO_SHORT_PASSWORD,EMAIL);
        userValidator.validate(user,errors);
        assertSingleError(NOT_ENOUGH_CHARACTERS);
    }

    @Test
    public void shouldFailValidationDueToTooBigPassword(){
        prepareForTest(NICKNAME,TOO_BIG_PASSWORD,EMAIL);
        userValidator.validate(user,errors);
        assertSingleError(TOO_MANY_CHARACTERS);
    }

    @Test
    public void shouldFailValidationDueToWrongEmailPattern(){
        prepareForTest(NICKNAME,PASSWORD,WRONG_EMAIL);
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
