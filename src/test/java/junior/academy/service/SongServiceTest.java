package junior.academy.service;


import junior.academy.dao.SongDao;
import junior.academy.domain.Author;
import junior.academy.domain.Song;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        assertEquals(songService.getAllSongs().size(),1);
    }

    @Test
    public void getSongById(){
        Song song=songs.get(0);
        when(songDao.getSongById(anyLong())).thenReturn(Optional.ofNullable(song));
        assertEquals(songService.getSongById(anyLong()),Optional.ofNullable(song));

    }

    @Test
    public void createOrUpdateSong(){
        doNothing().when(songDao).createOrUpdateSong(any(Song.class));
        songService.createOrUpdateSong(any(Song.class));
        verify(songDao,atLeastOnce()).createOrUpdateSong(any(Song.class));
    }

    @Test
    public void deleteSongById(){
        doNothing().when(songDao).deleteSongByIt(anyLong());
        songService.deleteSongById(anyLong());
        verify(songDao,atLeastOnce()).deleteSongByIt(anyLong());
    }

    @Test
    public void isSongPresent(){
        Song song=getSongList().get(0);
        when(songDao.getSongById(anyLong())).thenReturn(Optional.ofNullable(song));
        assertEquals(songService.isSongPresent(anyLong()),true);
    }

    public List<Song> getSongList(){
        Song song=new Song();
        song.setSongName("testSong");
        song.setAuthor(new Author());
        song.setMusicGenre("testGenre");
        song.setReleaseYear(1900);
        song.setYouTubeLink("www.youtube.com/test2");
        songs.add(song);
        return songs;
    }

}
