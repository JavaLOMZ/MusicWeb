package junior.academy.service;


import junior.academy.dao.DefaultDao;
import junior.academy.dao.SongDao;
import junior.academy.domain.Author;
import junior.academy.domain.MusicGenre;
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

import java.util.*;

public class SongServiceTest {
    @Mock
    SongDao songDao;

    @Mock
    DefaultDao defaultDao;

    @InjectMocks
    SongService songService;

    @Spy
    List<Song> songs = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        songs = getSongList();
    }

    @Test
    public void getAllSongs() {
        when(defaultDao.getAll(Song.class)).thenReturn(songs);
        assertEquals(songService.getAllSongs().size(), 2);
    }

    @Test
    public void getSongById() {
        Song song = songs.get(0);
        when(defaultDao.getById(eq(Song.class),anyLong())).thenReturn(Optional.ofNullable(song));
        assertEquals(songService.getSongById(song.getSongId()), Optional.ofNullable(song));

    }

    @Test
    public void createOrUpdateSong() {
        Song song = getSongList().get(0);
        doNothing().when(defaultDao).saveOrUpdate(song);
        song.setYouTubeLink(song.getYouTubeLink().replace("https://www.youtube.com/watch?v=", "https://www.youtube.com/embed/"));
        songService.createOrUpdateSong(song);
        verify(defaultDao, atLeastOnce()).saveOrUpdate(song);
    }

    @Test
    public void deleteSongById() {
        long id=songs.get(0).getSongId();
        doNothing().when(defaultDao).deleteById(eq(Song.class),anyLong());
        songService.deleteSongById(id);
        verify(defaultDao, atLeastOnce()).deleteById(eq(Song.class),anyLong());
    }

    @Test
    public void isSongPresentById() {
        Song song = songs.get(0);
        when(defaultDao.getById(eq(Song.class),anyLong())).thenReturn(Optional.ofNullable(song));
        assertEquals(songService.isSongPresent(song.getSongId()), true);
    }


    @Test
    public void getCommentsBySongId() {
        List<Song> songs = getSongList();
        when(songDao.getSongsByAuthorId(anyLong())).thenReturn(songs);
        assertEquals(songService.getSongsByAuthorId(anyLong()), songs);
    }

    @Test
    public void getMusicGenreTypes() {
        assertEquals(songService.getMusicGenreTypes(), new ArrayList<>(Arrays.asList(MusicGenre.values())));
    }

    @Test
    public void getNotRatedSongs() {
        List<Song> testSongs = songs;
        when(songDao.getNotRatedSongs(anyLong(), any(MusicGenre.class))).thenReturn(songs);
        assertEquals(songService.getNotRatedSongs(anyLong(), any(MusicGenre.class)), testSongs);
    }

    @Test
    public void getRatedSongs() {
        List<Song> testSongs = songs;
        when(songDao.getRatedSongs(anyLong())).thenReturn(songs);
        assertEquals(songService.getRatedSongs(anyLong()), testSongs);
    }

    @Test
    public void getUniqueSongByNameAndAuthorId() {
        Song testSong = songs.get(0);
        when(songDao.getUniqueSongByNameAndAuthor(anyString(), anyLong())).thenReturn(testSong);
        assertEquals(songService.getUniqueSongByNameAndAuthor(anyString(), anyLong()), testSong);
    }

    @Test
    public void getSongBySearchWord() {
        List<Song> testSongList = songs;
        when(songDao.getSongBySearchWord(anyString())).thenReturn(testSongList);
        assertEquals(songService.getSongBySearchWord(anyString()), testSongList);
    }

    @Test
    public void getListToSortElementsWithSearchWord() {
        List<Song> listToSortSongs;
        List<Song> specifiedSongsList = getSongBiggerList();
        String searchWord = null;
        when(songDao.getSongBySearchWord(searchWord)).thenReturn(songs);
        when(defaultDao.getAll(Song.class)).thenReturn(specifiedSongsList);
        if (searchWord != null && searchWord.compareTo("undefined") != 0 && searchWord.compareTo("null") != 0)
            listToSortSongs = songService.getSongBySearchWord(searchWord);
        else listToSortSongs = songService.getAllSongs();
        assertEquals(songService.getListToSortElementsWithSearchWord(searchWord).size(), listToSortSongs.size());
    }

    @Test
    public void getAllSongsSortedBySongName(){
        List<Song> songsSortedByName=getSongBiggerList();
        when(songDao.getSongBySearchWord(anyString())).thenReturn(songsSortedByName);
        songsSortedByName.sort(Comparator.comparing(Song::getSongName));
        assertEquals(songService.getAllSongsSortedBySongName(anyString()).get(0).getSongName(),"aTestSong");
    }

    @Test
    public void getAllSongsSortedBySongNameReversed(){
        List<Song> songsSortedByNameReversed=getSongBiggerList();
        when(songDao.getSongBySearchWord(anyString())).thenReturn(songsSortedByNameReversed);
        songsSortedByNameReversed.sort(Comparator.comparing(Song::getSongName).reversed());
        assertEquals(songService.getAllSongsSortedBySongNameReversed(anyString()).get(0).getSongName(),"testSong");
    }

    @Test
    public void getAllSongsSortedByMusicGenre(){
        List<Song> songsSortedByMusicGenre=getSongBiggerList();
        when(songDao.getSongBySearchWord(anyString())).thenReturn(songsSortedByMusicGenre);
        songsSortedByMusicGenre.sort(Comparator.comparing(Song::getMusicGenre));
        assertEquals(songService.getAllSongsSortedByMusicGenre(anyString()).get(0).getMusicGenre(),MusicGenre.HIPHOP);
    }

    @Test
    public void getAllSongsSortedByMusicGenreReversed(){
        List<Song> songsSortedByMusicGenreReversed=getSongBiggerList();
        when(songDao.getSongBySearchWord(anyString())).thenReturn(songsSortedByMusicGenreReversed);
        songsSortedByMusicGenreReversed.sort(Comparator.comparing(Song::getMusicGenre).reversed());
        assertEquals(songService.getAllSongsSortedByMusicGenreReversed(anyString()).get(0).getMusicGenre(),MusicGenre.ROCK);
    }

    @Test
    public void getAllSongsSortedByReleaseYear(){
        List<Song> songsSortedByReleaseYear=getSongBiggerList();
        when(songDao.getSongBySearchWord(anyString())).thenReturn(songsSortedByReleaseYear);
        songsSortedByReleaseYear.sort(Comparator.comparing(Song::getReleaseYear));
        assertEquals(songService.getAllSongsSortedByReleaseYear(anyString()).get(0).getReleaseYear(),1900);
    }

    @Test
    public void getAllSongsSortedByReleaseYearReversed(){
        List<Song> songsSortedByReleaseYearReversed=getSongBiggerList();
        when(songDao.getSongBySearchWord(anyString())).thenReturn(songsSortedByReleaseYearReversed);
        songsSortedByReleaseYearReversed.sort(Comparator.comparing(Song::getReleaseYear).reversed());
        assertEquals(songService.getAllSongsSortedByReleaseYearReversed(anyString()).get(0).getReleaseYear(),1905);
    }

    @Test
    public void getAllSongsSortedByAuthorName(){
        List<Song> songsSortedByAuthorName=getSongBiggerList();
        when(songDao.getSongBySearchWord(anyString())).thenReturn(songsSortedByAuthorName);
        songsSortedByAuthorName.sort(Comparator.comparing(song -> song.getAuthor().getName()));
        assertEquals(songService.getAllSongsSortedByAuthorName(anyString()).get(0).getAuthor().getName(),"aNieLufycer");
    }

    @Test
    public void getAllSongsSortedByAuthorNameReversed (){
        List<Song> songsSortedByAuthorNameReversed=getSongBiggerList();
        when(songDao.getSongBySearchWord(anyString())).thenReturn(songsSortedByAuthorNameReversed);
        songsSortedByAuthorNameReversed.sort((o1, o2) -> o2.getAuthor().getName().compareTo(o1.getAuthor().getName()));
        assertEquals(songService.getAllSongsSortedByAuthorNameReversed(anyString()).get(0).getAuthor().getName(),"lucyfer");
    }


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

    public List<Song> getSongBiggerList() {
        List<Song> songList = new ArrayList<>();
        Author author = new Author();
        Author author2 = new Author();
        author.setName("lucyfer");
        author2.setName("aNieLufycer");
        Song song = new Song();
        song.setSongName("testSong");
        song.setAuthor(author);
        song.setMusicGenre(MusicGenre.HIPHOP);
        song.setReleaseYear(1905);
        song.setYouTubeLink("www.youtube.com/test");

        Song song2 = new Song();
        song2.setSongName("aTestSong");
        song2.setAuthor(author2);
        song2.setMusicGenre(MusicGenre.ROCK);
        song2.setReleaseYear(1900);
        song2.setYouTubeLink("www.youtube.com/atest");
        songList.add(song);
        songList.add(song2);
        return songList;
    }
}
