package junior.academy.controller;


import junior.academy.domain.Song;
import junior.academy.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    SongService songService;

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
    public void createOrUpdateSong(@RequestBody Song song) {
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
}
