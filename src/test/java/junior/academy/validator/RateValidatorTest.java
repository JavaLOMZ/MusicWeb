package junior.academy.validator;

import junior.academy.domain.Author;
import junior.academy.domain.Rate;
import junior.academy.domain.Song;
import junior.academy.domain.User;
import junior.academy.service.SongService;
import junior.academy.service.UserService;
import junior.academy.util.ErrorCodes;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RateValidatorTest implements ErrorCodes{

    private Rate rate;
    private Errors errors;

    @Mock
    UserService userService;

    @Mock
    SongService songService;

    @InjectMocks
    private RateValidator rateValidator;

    @Test
    public void shouldPassValidation(){
        prepareForTestWithValidRateValue();
        when(userService.isUserPresent(anyLong())).thenReturn(true);
        when(songService.isSongPresent(anyLong())).thenReturn(true);
        rateValidator.validate(rate, errors);
        assertEquals(0, errors.getErrorCount());
    }

    @Test
    public void shouldFailUserPresentTest(){
        prepareForTestWithValidRateValue();
        when(userService.isUserPresent(anyLong())).thenReturn(false);
        when(songService.isSongPresent(anyLong())).thenReturn(true);
        rateValidator.validate(rate, errors);
        assertSingleError(USER_DOES_NOT_EXIST);
    }

    @Test
    public void shouldFailSongPresentTest(){
        prepareForTestWithValidRateValue();
        when(userService.isUserPresent(anyLong())).thenReturn(true);
        when(songService.isSongPresent(anyLong())).thenReturn(false);
        rateValidator.validate(rate, errors);
        assertSingleError(SONG_DOES_NOT_EXIST);
    }

    @Test
    public void shouldFailValueTooSmallTest(){
        prepareForTestWithInvalidRateValue();
        int rateValueTooSmall = 0;
        rate.setRateValue(rateValueTooSmall);
        rateValidator.validate(rate, errors);
        assertSingleError(VALUE_TOO_SMALL);
    }

    @Test
    public void shouldFailValueTooBigTest(){
        prepareForTestWithInvalidRateValue();
        int rateValueTooBig = 11;
        rate.setRateValue(rateValueTooBig);
        rateValidator.validate(rate, errors);
        assertSingleError(VALUE_TOO_BIG);
    }

    private void assertSingleError(String errorCode) {
        assertEquals(1, errors.getErrorCount());
        assertEquals(errorCode, errors.getAllErrors().get(0).getCode());
    }

    private void prepareForTestWithValidRateValue() {
        rate = new Rate(new User(), new Song());
        int rateValueValid = 5;
        rate.setRateValue(rateValueValid);
        errors = new BeanPropertyBindingResult(rate, "author");
    }

    private void prepareForTestWithInvalidRateValue() {
        rate = new Rate(new User(), new Song());
        when(userService.isUserPresent(anyLong())).thenReturn(true);
        when(songService.isSongPresent(anyLong())).thenReturn(true);
        errors = new BeanPropertyBindingResult(rate, "author");
    }
}
