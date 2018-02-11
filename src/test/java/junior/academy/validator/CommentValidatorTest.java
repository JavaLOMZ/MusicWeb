package junior.academy.validator;


import junior.academy.domain.Comment;
import junior.academy.domain.Song;
import junior.academy.domain.User;
import junior.academy.service.SongService;
import junior.academy.service.UserService;
import junior.academy.util.ErrorCodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentValidatorTest implements ErrorCodes{

    private static String COMMENT_TEXT="comment";
    private static User USER=new User();
    private static Song SONG=new Song();

    private Errors errors;
    private Comment comment;

    @Mock
    private UserService userService;

    @Mock
    private SongService songService;

    @InjectMocks
    private CommentValidator commentValidator;

    @Test
    public void supportsShouldReturnFalse(){
        assertEquals(false,commentValidator.supports(Object.class));
    }

    @Test
    public void supportsShouldReturnTrue(){
        assertEquals(true,commentValidator.supports(Comment.class));
    }

    @Test
    public void shouldPassValidation(){
        prepareForTest(COMMENT_TEXT,USER,SONG);
        when(userService.isUserPresent(anyLong())).thenReturn(true);
        when(songService.isSongPresent(anyLong())).thenReturn(true);
        commentValidator.validate(comment,errors);
        assertEquals(0,errors.getErrorCount());
    }

    @Test
    public void shouldFailValidationDueToEmptyCommentText(){
        prepareForTest(null,USER,SONG);
        when(userService.isUserPresent(anyLong())).thenReturn(true);
        when(songService.isSongPresent(anyLong())).thenReturn(true);
        commentValidator.validate(comment,errors);
        assertSingleError(EMPTY);
    }

    @Test
    public void shouldFailValidationDueToTooLongComment(){
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<101;i++){
            stringBuilder.append("a");
        }
        String TOO_LONG_COMMENT_TEXT=stringBuilder.toString();
        prepareForTest(TOO_LONG_COMMENT_TEXT,USER,SONG);
        when(userService.isUserPresent(anyLong())).thenReturn(true);
        when(songService.isSongPresent(anyLong())).thenReturn(true);
        commentValidator.validate(comment,errors);
        assertSingleError(TOO_MANY_CHARACTERS);
    }

    @Test
    public void shouldFailValidationDueToLackOfUser(){
        prepareForTest(COMMENT_TEXT,USER,SONG);
        when(userService.isUserPresent(anyLong())).thenReturn(false);
        when(songService.isSongPresent(anyLong())).thenReturn(true);
        commentValidator.validate(comment,errors);
        assertSingleError(USER_DOES_NOT_EXIST);
    }

    @Test
    public void shouldFailValidationDueToLackOfSong(){
        prepareForTest(COMMENT_TEXT,USER,SONG);
        when(userService.isUserPresent(anyLong())).thenReturn(true);
        when(songService.isSongPresent(anyLong())).thenReturn(false);
        commentValidator.validate(comment,errors);
        assertSingleError(SONG_DOES_NOT_EXIST);
    }

    private void assertSingleError(String errorCode){
        assertEquals(1,errors.getErrorCount());
        assertEquals(errorCode,errors.getAllErrors().get(0).getCode());
    }

    private void prepareForTest(String commentText, User user, Song song){
        comment = new Comment(commentText,user,song);
        errors=new BeanPropertyBindingResult(comment,"comment");
    }

}

