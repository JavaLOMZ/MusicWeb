package junior.academy.controller;


import junior.academy.domain.MusicGenre;
import junior.academy.domain.Song;
import junior.academy.service.AuthorService;
import junior.academy.service.RandomSongService;
import junior.academy.service.RateService;
import junior.academy.service.SongService;
import junior.academy.validator.SongValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    SongService songService;

    @Autowired
    AuthorService authorService;

    @Autowired
    RateService rateService;

    @Autowired
    RandomSongService randomSongService;

    @Autowired
    SongValidator songValidator;

    @InitBinder()
    protected void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setValidator(this.songValidator);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<Song> getSongById(@PathVariable long songId) {
        if (songService.isSongPresent(songId)) {
            return new ResponseEntity<Song>(songService.getSongById(songId).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @PostMapping
    public void createOrUpdateSong(@RequestBody @Valid Song song) {
        songService.createOrUpdateSong(song);
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity deleteSongById(@PathVariable long songId) {
        if (songService.isSongPresent(songId)) {
            songService.deleteSongById(songId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("author/{authorId}")
    public ResponseEntity<List<Song>> getSongsByAuthorId(@PathVariable long authorId){
        if(authorService.isAuthorPresent(authorId)){
            return new ResponseEntity<>(songService.getSongsByAuthorId(authorId),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/musicGenre")
    public ArrayList<MusicGenre> getMusicGenreTypes() {
        return songService.getMusicGenreTypes();
    }


    @GetMapping("/user/recommendedSongs/{userId}")
    public List<Song> getRandomSongsByUserPreferences(@PathVariable long userId){
        return randomSongService.getRandomSongs(userId);
    }

    @GetMapping("/songName/{songName}/{authorId}")
    public ResponseEntity<Song> getSongByNameAndAuthor(@PathVariable String songName, @PathVariable long authorId){
        if(songService.getUniqueSongByNameAndAuthor(songName,authorId)!=null) {
            return new ResponseEntity<>(songService.getUniqueSongByNameAndAuthor(songName, authorId), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/{searchWord}")
    public List<Song> getSongBySearchWord(@PathVariable String searchWord){
        return songService.getSongBySearchWord(searchWord);
    }

    @GetMapping("/sortedBySongName/{searchWord}")
    public List<Song> getAllSongsSortedBySongName(@PathVariable String searchWord){
        return songService.getAllSongsSortedBySongName(searchWord);
    }

    @GetMapping("/sortedBySongNameReversed/{searchWord}")
    public List<Song> getAllSongsSortedBySongNameReversed(@PathVariable String searchWord){
        return songService.getAllSongsSortedBySongNameReversed(searchWord);
    }

    @GetMapping("/sortedByMusicGenre/{searchWord}")
    public List<Song> getAllSongsSortedByMusicGenre(@PathVariable String searchWord){
        return songService.getAllSongsSortedByMusicGenre(searchWord);
    }

    @GetMapping("/sortedByMusicGenreReversed/{searchWord}")
    public List<Song> getAllSongsSortedByMusicGenreReversed(@PathVariable String searchWord){
        return songService.getAllSongsSortedByMusicGenreReversed(searchWord);
    }

    @GetMapping("/sortedByReleaseYear/{searchWord}")
    public List<Song> getAllSongsSortedByReleaseYear(@PathVariable String searchWord){
        return songService.getAllSongsSortedByReleaseYear(searchWord);
    }

    @GetMapping("/sortedByReleaseYearReversed/{searchWord}")
    public List<Song> getAllSongsSortedByReleaseYearReversed(@PathVariable String searchWord){
        return songService.getAllSongsSortedByReleaseYearReversed(searchWord);
    }

    @GetMapping("/sortedByAuthorName/{searchWord}")
    public List<Song> getAllSongsSortedByAuthorName(@PathVariable String searchWord){
        return songService.getAllSongsSortedByAuthorName(searchWord);
    }

    @GetMapping("/sortedByAuthorNameReversed/{searchWord}")
    public List<Song> getAllSongsSortedByAuthorNameReversed(@PathVariable String searchWord){
        return songService.getAllSongsSortedByAuthorNameReversed(searchWord);
    }

    @GetMapping("/sortedByAverageRate/{searchWord}")
    public List<Song> getAllSongsSortedByAverageRate(@PathVariable String searchWord){
        return songService.getAllSongsSortedByAverageRate(searchWord);
    }

    @GetMapping("/sortedByAverageRateReversed/{searchWord}")
    public List<Song> getAllSongsSortedByAverageRateReversed(@PathVariable String searchWord){
        return songService.getAllSongsSortedByAverageRateReversed(searchWord);
    }
}
