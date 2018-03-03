package junior.academy.validator;

import junior.academy.domain.User;


//import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import junior.academy.service.UserService;
import junior.academy.util.ErrorCodes;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

import static java.util.Optional.empty;

@Component
public class UserValidator implements Validator, ErrorCodes {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", EMPTY);
        if (!userService.getUserByUsername(user.getNickname()).equals(empty()) && user.getUserId() == 0) {
            errors.rejectValue("nickname", USERNAME_TAKEN);
        }
        if (!userService.getUserByEmail(user.getEmail()).equals(empty()) && user.getUserId() == 0) {
            errors.rejectValue("nickname", EMAIL_TAKEN);
        }
        if (user.getNickname().length() < 3) {
            errors.rejectValue("nickname", NOT_ENOUGH_CHARACTERS);
        }
        if (user.getNickname().length() > 25) {
            errors.rejectValue("nickname", TOO_MANY_CHARACTERS);
        }
        if (user.getPassword().length() < 5) {
            errors.rejectValue("password", NOT_ENOUGH_CHARACTERS);
        }
        if (user.getPassword().length() > 12) {
            errors.rejectValue("password", TOO_MANY_CHARACTERS);
        }
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            errors.rejectValue("email", WRONG_EMAIL_PATTERN);
        }
    }
}
