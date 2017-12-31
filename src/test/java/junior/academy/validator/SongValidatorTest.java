package junior.academy.validator;


import junior.academy.domain.Author;
import junior.academy.domain.MusicGenre;
import junior.academy.domain.Song;
import junior.academy.service.SongService;
import junior.academy.util.ErrorCodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SongValidatorTest implements ErrorCodes{

    private static String SONGNAME="songName";
    private static MusicGenre MUSIC_GENRE=MusicGenre.HIPHOP;
    private static int RELEASE_YEAR=2017;
    private static String YOUTUBE_LINK="https://www.youtube.com/watch?v=Yz7HPK6GOLA";
    private static Author AUTHOR=new Author();

    private Errors errors;
    private Song song;

    @Mock
    SongService songService;

    @InjectMocks
    private SongValidator songValidator;


    @Test
    public void shouldPassValidation(){
        prepareForTest(SONGNAME,MUSIC_GENRE,RELEASE_YEAR,YOUTUBE_LINK,AUTHOR);
        songValidator.validate(song,errors);
        assertEquals(0,errors.getErrorCount());
    }

    @Test
    public void shouldFailValidationDueToEmptySongName(){
        prepareForTest(null,MUSIC_GENRE,RELEASE_YEAR,YOUTUBE_LINK,AUTHOR);
        when(songService.getUniqueSongByNameAndAuthor(anyString(),anyLong())).thenReturn(null);
        songValidator.validate(song,errors);
        assertSingleError(EMPTY);

    }

    @Test
    public void shouldFailValidationDueToTooShortSongName(){
        String TOO_SHORT_SONGNAME="s";
        prepareForTest(TOO_SHORT_SONGNAME,MUSIC_GENRE,RELEASE_YEAR,YOUTUBE_LINK,AUTHOR);
        when(songService.getUniqueSongByNameAndAuthor(anyString(),anyLong())).thenReturn(null);
        songValidator.validate(song,errors);
        assertSingleError(NOT_ENOUGH_CHARACTERS);

    }

    @Test
    public void shouldFailValidationDueToTooLongSongName(){
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<101;i++){
            stringBuilder.append("a");
        }
        String TOO_LONG_SONGNAME=stringBuilder.toString();
        prepareForTest(TOO_LONG_SONGNAME,MUSIC_GENRE,RELEASE_YEAR,YOUTUBE_LINK,AUTHOR);
        when(songService.getUniqueSongByNameAndAuthor(anyString(),anyLong())).thenReturn(null);
        songValidator.validate(song,errors);
        assertSingleError(TOO_MANY_CHARACTERS);
    }

    @Test
    public void shouldFailValidationDueToTooHighReleaseYear(){
        int TOO_HIGH_YEAR=3000;
        prepareForTest(SONGNAME,MUSIC_GENRE,TOO_HIGH_YEAR,YOUTUBE_LINK,AUTHOR);
        when(songService.getUniqueSongByNameAndAuthor(anyString(),anyLong())).thenReturn(null);
        songValidator.validate(song,errors);
        assertSingleError(SONG_TOO_YOUNG);
    }

    @Test
    public void shouldFailValidationDueToWrongYouTubeLinkPattern(){
        String WRONG_YOUTUBE_LINK="asdasdasd";
        prepareForTest(SONGNAME,MUSIC_GENRE,RELEASE_YEAR,WRONG_YOUTUBE_LINK,AUTHOR);
        when(songService.getUniqueSongByNameAndAuthor(anyString(),anyLong())).thenReturn(null);
        songValidator.validate(song,errors);
        assertSingleError(BAD_YOUTUBE_LINK);
    }

//    @Test
//    public void shouldFailValidationDueToTheSameSongNameForAuthor(){
//        prepareForTest(SONGNAME,MUSIC_GENRE,RELEASE_YEAR,YOUTUBE_LINK,AUTHOR);
//        when(songService.getUniqueSongByNameAndAuthor(anyString(),anyLong())).thenReturn(java.util.Optional.of(new Song()));
//        songValidator.validate(song,errors);
//        assertSingleError(SONGNAME_TAKEN);
//    }


    private void assertSingleError(String errorCode){
        assertEquals(1,errors.getErrorCount());
        assertEquals(errorCode,errors.getAllErrors().get(0).getCode());
    }

    private void prepareForTest(String songName, MusicGenre musicGenre, int releaseYear, String youTubeLink, Author author){
        song=new Song(songName,musicGenre,releaseYear,youTubeLink,author);
        errors=new BeanPropertyBindingResult(song,"song");
    }
}
