package junior.academy.validator;

import junior.academy.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", "NICKNAME_EMPTY");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EMAIL_EMPTY");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "PASSWORD_EMPTY");
    }
}
