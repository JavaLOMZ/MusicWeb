package junior.academy.controller;

import junior.academy.domain.Author;
import junior.academy.domain.MusicGenre;
import junior.academy.domain.Song;
import junior.academy.service.AuthorService;
import junior.academy.service.RandomSongService;
import junior.academy.service.RateService;
import junior.academy.service.SongService;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SongControllerTest {

    @Mock
    SongService songService;

    @Mock
    RandomSongService randomSongService;

    @Mock
    AuthorService authorService;

    @Mock
    RateService rateService;

    @InjectMocks
    SongController songController;

    @Spy
    List<Song> songs = new ArrayList<>();



    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        songs = getSongList();
    }

    @Test
    public void getAllSongs() {
        when(songService.getAllSongs()).thenReturn(songs);
        assertEquals(songController.getAllSongs().size(), 1);
    }

    @Test
    public void getSongByIdWhenPresent() {
        Song song = songs.get(0);
        when(songService.isSongPresent(anyLong())).thenReturn(true);
        when(songService.getSongById(anyLong())).thenReturn(Optional.ofNullable(song));
        assertEquals(songController.getSongById(anyLong()), new ResponseEntity<Song>(song, HttpStatus.OK));
    }

    @Test
    public void getSongByIdWhenNotPresent() {
        when(songService.isSongPresent(anyLong())).thenReturn(false);
        assertEquals(songController.getSongById(anyLong()), new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Test
    public void createOrUpdateSong() {
        doNothing().when(songService).createOrUpdateSong(any(Song.class));
        songController.createOrUpdateSong(any(Song.class));
        verify(songService, atLeastOnce()).createOrUpdateSong(any(Song.class));
    }

    @Test
    public void deleteSongByIdWhenPresent() {
        when(songService.isSongPresent(anyLong())).thenReturn(true);
        doNothing().when(songService).deleteSongById(anyLong());
        assertEquals(songController.deleteSongById(anyLong()), new ResponseEntity(HttpStatus.OK));
        verify(songService, atLeastOnce()).deleteSongById(anyLong());
    }

    @Test
    public void deleteSongByIdWhenNotPresent() {
        when(songService.isSongPresent(anyLong())).thenReturn(false);
        assertEquals(songController.deleteSongById(anyLong()), new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @Test
    public void getSongsByAuthorIdWhenPresent(){
        List<Song>songs=getSongList();
        when(authorService.isAuthorPresent(anyLong())).thenReturn(true);
        when(songService.getSongsByAuthorId(anyLong())).thenReturn(songs);
        assertEquals(songController.getSongsByAuthorId(anyLong()),new ResponseEntity<>(songs,HttpStatus.OK));
    }

    @Test
    public void getSongsByAuthorIdWhenNotPresent(){
        when(authorService.isAuthorPresent(anyLong())).thenReturn(false);
        assertEquals(songController.getSongsByAuthorId(anyLong()),new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @Test
    public void getMusicGenreTypes(){
        ArrayList<MusicGenre>musicGenres=new ArrayList<>(Arrays.asList(MusicGenre.values()));
        when(songService.getMusicGenreTypes()).thenReturn(musicGenres);
        assertEquals(songController.getMusicGenreTypes(),musicGenres);
    }

    @Test
    public void getSongsBySearchWord(){
        List<Song> songsListTest = songs;
        when(songService.getSongBySearchWord(anyString())).thenReturn(songsListTest);
        assertEquals(songController.getSongBySearchWord((anyString())), songsListTest);
    }

    @Test
    public void getRandomSongsByPreference(){
        List<Song> songListTest = songs;
        when(randomSongService.getRandomSongs(anyLong())).thenReturn(songListTest);
        assertEquals(songController.getRandomSongsByUserPreferences(anyLong()), songListTest);
    }


    //todo test do comparatorow

    public List<Song> getSongList() {
        Song song = new Song();
        song.setSongName("testSong");
        song.setAuthor(new Author());
        song.setMusicGenre(MusicGenre.HIPHOP);
        song.setReleaseYear(1900);
        song.setYouTubeLink("www.youtube.com/test2");
        songs.add(song);
        return songs;
    }

//    public List<Rate> getRateList() {
//        Rate rate = new Rate();
//        rate.setRateValue(10);
//
//        Rate rate2=new Rate();
//        rate2.setRateValue(20);
//
//        rates.add(rate);
//        rates.add(rate2);
//        return rates;
//    }

}
