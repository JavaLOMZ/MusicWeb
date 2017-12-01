package junior.academy.validator;

import junior.academy.domain.Rate;
import junior.academy.util.ErrorCodes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class RateValidator implements Validator, ErrorCodes {

    @Override
    public boolean supports(Class<?> aClass) {
        return Rate.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Rate rate = (Rate) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "songId", EMPTY);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rateValue", EMPTY);
        if (rate.getRateValue() > 10) {
            errors.rejectValue("rateValue", VALUE_TOO_BIG);
        }
        if (rate.getRateValue() < 1) {
            errors.rejectValue("rateValue", VALUE_TOO_SMALL);
        }

    }
}
