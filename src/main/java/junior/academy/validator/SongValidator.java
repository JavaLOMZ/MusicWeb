package junior.academy.validator;



import junior.academy.domain.Song;
import junior.academy.service.SongService;
import junior.academy.util.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class SongValidator implements Validator, ErrorCodes{

    @Autowired
    SongService songService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Song.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Song song= (Song) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"songName",EMPTY);
        if(song.getSongName()!=null) {
            if (song.getSongName().length() < 2) {
                errors.rejectValue("songName", NOT_ENOUGH_CHARACTERS);
            }
            if (song.getSongName().length() > 100) {
                errors.rejectValue("songName", TOO_MANY_CHARACTERS);
            }
        }
        if(song.getReleaseYear()> LocalDate.now().getYear()){
            errors.rejectValue("releaseYear",SONG_TOO_YOUNG);
        }
        if(!song.getYouTubeLink().contains("https://www.youtube.com/watch?v=") && song.getYouTubeLink().length()>0){
           errors.rejectValue("youTubeLink",BAD_YOUTUBE_LINK);
        }


        if(songService.getUniqueSongByNameAndAuthor(song.getSongName(),song.getAuthor().getAuthorId())!=null){
            errors.rejectValue("songName",SONGNAME_TAKEN);
        }
    }
}
