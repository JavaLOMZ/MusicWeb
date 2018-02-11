package junior.academy.validator;
import junior.academy.domain.Author;
import junior.academy.service.AuthorService;
import junior.academy.util.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class AuthorValidator implements Validator, ErrorCodes {

    @Autowired
    AuthorService authorService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Author.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Author author = (Author) object;
        Optional<Author> authorCheck=authorService.getAuthorByName(author.getName());

        if(authorCheck!=null){
            errors.rejectValue("name", NAME_TAKEN);
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", NOT_ENOUGH_CHARACTERS);
        if(author.getName().length() > 25){
            errors.rejectValue("name", TOO_MANY_CHARACTERS);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "countryOfOrigin", NOT_ENOUGH_CHARACTERS);
        if(author.getCountryOfOrigin().length() > 25){
            errors.rejectValue("countryOfOrigin", TOO_MANY_CHARACTERS);
        }

        if(author.getYearOfBirth() > LocalDate.now().getYear()){
            errors.rejectValue("yearOfBirth", AUTHOR_TOO_YOUNG);
        }
    }
}
