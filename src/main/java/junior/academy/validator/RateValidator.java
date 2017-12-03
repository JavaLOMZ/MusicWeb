package junior.academy.validator;

import junior.academy.dao.SongDao;
import junior.academy.dao.UserDao;
import junior.academy.domain.Rate;
import junior.academy.domain.Song;
import junior.academy.domain.User;
import junior.academy.util.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class RateValidator implements Validator, ErrorCodes {

    @Autowired
    UserDao userDao;

    @Autowired
    SongDao songDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return Rate.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Rate rate = (Rate) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rateValue", EMPTY);
        if (rate.getRateValue() > 10) {
            errors.rejectValue("rateValue", VALUE_TOO_BIG);
        }
        if (rate.getRateValue() < 1) {
            errors.rejectValue("rateValue", VALUE_TOO_SMALL);
        }
        if(!userDao.getUserById(rate.getUser().getUserId()).isPresent()){
            errors.rejectValue("user", USER_DOES_NOT_EXIST);
        }
        if(!songDao.getSongById(rate.getSong().getSongId()).isPresent()){
            errors.rejectValue("song", SONG_DOES_NOT_EXIST);
        }
    }
}
