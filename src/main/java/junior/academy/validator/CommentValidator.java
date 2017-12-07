package junior.academy.validator;

import junior.academy.domain.Comment;
import junior.academy.service.SongService;
import junior.academy.service.UserService;
import junior.academy.util.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CommentValidator implements Validator, ErrorCodes {

    @Autowired
    UserService userService;

    @Autowired
    SongService songService;

    @Override
    public boolean supports(Class aClass) {
        return Comment.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Comment comment= (Comment) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"commentText",EMPTY);
        if(comment.getCommentText()!=null) {
            if (comment.getCommentText().length() > 100) {
                errors.rejectValue("commentText", TOO_MANY_CHARACTERS);
            }
        }
        if(!userService.isUserPresent(comment.getUser().getUserId())){
            errors.rejectValue("user", USER_DOES_NOT_EXIST);
        }
        if(!songService.isSongPresent(comment.getSong().getSongId())){
            errors.rejectValue("song", SONG_DOES_NOT_EXIST);
        }

    }
}
