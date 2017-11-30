package junior.academy.validator;

import junior.academy.domain.User;


//import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private static final String NOT_ENOUGH_CHARACTERS="NOT_ENOUGH_CHARACTERS";
    private static final String TOO_MANY_CHARACTERS="TOO_MANY_CHARACTERS";
    private static final String EMPTY="EMPTY_FIELD";
    private static final String WRONG_EMAIL_PATTERN="WRONG_EMAIL_PATTERN";


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", EMPTY);
        if(user.getNickname().length()<2){
            errors.rejectValue("nickname",NOT_ENOUGH_CHARACTERS);
        }
        if(user.getPassword().length()<5){
            errors.rejectValue("password",NOT_ENOUGH_CHARACTERS);
        }
        if(user.getPassword().length()>12){
            errors.rejectValue("password",TOO_MANY_CHARACTERS);
        }
        if(!EmailValidator.getInstance().isValid(user.getEmail())){
            errors.rejectValue("email",WRONG_EMAIL_PATTERN);
        }


    }
}
