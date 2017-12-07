package junior.academy.validator;

import junior.academy.domain.Rate;
import junior.academy.service.SongService;
import junior.academy.service.UserService;
import junior.academy.util.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RateValidator implements Validator, ErrorCodes {

    @Autowired
    UserService userService;

    @Autowired
    SongService songService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Rate.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Rate rate = (Rate) object;
        if (rate.getRateValue() > 10) {
            errors.rejectValue("rateValue", VALUE_TOO_BIG);
        }
        if (rate.getRateValue() < 1) {
            errors.rejectValue("rateValue", VALUE_TOO_SMALL);
        }
        if(!userService.isUserPresent(rate.getUser().getUserId())){
            errors.rejectValue("user", USER_DOES_NOT_EXIST);
        }
        if(!songService.isSongPresent(rate.getSong().getSongId())){
            errors.rejectValue("song", SONG_DOES_NOT_EXIST);
        }
    }
}
