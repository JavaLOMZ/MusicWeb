package junior.academy.service;


import junior.academy.dao.SongDao;
import junior.academy.domain.Author;
import junior.academy.domain.MusicGenre;
import junior.academy.domain.Song;
import junior.academy.domain.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

import java.util.*;

public class SongServiceTest {
    @Mock
    SongDao songDao;

    @InjectMocks
    SongService songService;

    @Spy
    List<Song> songs=new ArrayList<>();

    @BeforeClass
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        songs=getSongList();
    }

    @Test
    public void getAllSongs(){
        when(songDao.getAllSongs()).thenReturn(songs);
        assertEquals(songService.getAllSongs().size(),2);
    }

    @Test
    public void getSongById(){
        Song song=songs.get(0);
        when(songDao.getSongById(anyLong())).thenReturn(Optional.ofNullable(song));
        assertEquals(songService.getSongById(anyLong()),Optional.ofNullable(song));

    }

    @Test
    public void createOrUpdateSong(){
        Song song=getSongList().get(0);
        doNothing().when(songDao).createOrUpdateSong(song);
        song.setYouTubeLink(song.getYouTubeLink().replace("https://www.youtube.com/watch?v=", "https://www.youtube.com/embed/"));
        songService.createOrUpdateSong(song);
        verify(songDao,atLeastOnce()).createOrUpdateSong(song);
    }

    @Test
    public void deleteSongById(){
        doNothing().when(songDao).deleteSongById(anyLong());
        songService.deleteSongById(anyLong());
        verify(songDao,atLeastOnce()).deleteSongById(anyLong());
    }

    @Test
    public void isSongPresent(){
        Song song=getSongList().get(0);
        when(songDao.getSongById(anyLong())).thenReturn(Optional.ofNullable(song));
        assertEquals(songService.isSongPresent(anyLong()),true);
    }

    @Test
    public void getCommentsBySongId(){
        List<Song> songs=getSongList();
        when(songDao.getSongsByAuthorId(anyLong())).thenReturn(songs);
        assertEquals(songService.getSongsByAuthorId(anyLong()),songs);
    }

    @Test
    public void getMusicGenreTypes(){
        assertEquals(songService.getMusicGenreTypes(),new ArrayList<>(Arrays.asList(MusicGenre.values())));
    }


    public List<Song> getSongList(){
        Song song=new Song();
        song.setSongName("testSong");
        song.setAuthor(new Author());
        //song.setMusicGenre("testGenre");
        song.setMusicGenre(MusicGenre.HIPHOP);
        song.setReleaseYear(1900);
        song.setYouTubeLink("www.youtube.com/test2");
        songs.add(song);
        return songs;
    }
}
