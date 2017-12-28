package junior.academy.service;

import junior.academy.dao.SongDao;
import junior.academy.domain.MusicGenre;
import junior.academy.domain.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SongService {



    @Autowired
    SongDao songDao;

    @Autowired
    RateService rateService;

    public Optional<Song> getSongById(long songId) {
        return songDao.getSongById(songId);
    }

    public List<Song> getAllSongs() {
        return songDao.getAllSongs();
    }

    public void createOrUpdateSong(Song song) {
        song.setYouTubeLink(song.getYouTubeLink().replace("https://www.youtube.com/watch?v=", "https://www.youtube.com/embed/"));
        songDao.createOrUpdateSong(song);
    }

    public void deleteSongById(long songId) {
        songDao.deleteSongById(songId);
    }

    public List<Song> getSongsByAuthorId(long authorId) {
        return songDao.getSongsByAuthorId(authorId);
    }

    public ArrayList<MusicGenre> getMusicGenreTypes() {
        return new ArrayList<>(Arrays.asList(MusicGenre.values()));
    }

    public List<Song> getNotRatedSongs(long userId, MusicGenre musicGenre) {
        return songDao.getNotRatedSongs(userId, musicGenre);
    }

    public List<Song> getRatedSongs(long userId) {
        return songDao.getRatedSongs(userId);
    }

    public Optional<Song> getUniqueSongByNameAndAuthor(String songName, long authorId){
        return songDao.getUniqueSongByNameAndAuthor(songName,authorId);
    }

    public List<Song> getSongBySearchWord(String searchWord){
        return songDao.getSongBySearchWord(searchWord);
    }

    public boolean isSongPresent(long songId) {
        return songDao.getSongById(songId).isPresent();
    }

    public boolean isSongPresent(String songName, long authorId){
        return songDao.getUniqueSongByNameAndAuthor(songName, authorId).isPresent();
    }

    public List<Song> getListToSortElementsWithSearchWord(String searchWord){
        List<Song>listToSortSongs;
        if (searchWord != null && searchWord.compareTo("undefined") != 0 && searchWord.compareTo("null") != 0)
            listToSortSongs = getSongBySearchWord(searchWord);
        else listToSortSongs = getAllSongs();
        return listToSortSongs;
    }

    public List<Song> getAllSongsSortedBySongName (String searchWord){
        List<Song>songsSortedByName=getListToSortElementsWithSearchWord(searchWord);
        songsSortedByName.sort(Comparator.comparing(Song::getSongName));
        return songsSortedByName;
    }

    public List<Song> getAllSongsSortedBySongNameReversed (String searchWord){
        List<Song>songsSortedByNameReversed=getListToSortElementsWithSearchWord(searchWord);
        songsSortedByNameReversed.sort(Comparator.comparing(Song::getSongName).reversed());
        return songsSortedByNameReversed;
    }

    public List<Song> getAllSongsSortedByMusicGenre (String searchWord){
        List<Song>songsSortedByMusicGenre=getListToSortElementsWithSearchWord(searchWord);
        songsSortedByMusicGenre.sort(Comparator.comparing(Song::getMusicGenre));
        return songsSortedByMusicGenre;
    }

    public List<Song> getAllSongsSortedByMusicGenreReversed (String searchWord){
        List<Song>songsSortedByMusicGenreReversed=getListToSortElementsWithSearchWord(searchWord);
        songsSortedByMusicGenreReversed.sort(Comparator.comparing(Song::getMusicGenre).reversed());
        return songsSortedByMusicGenreReversed;
    }

    public List<Song> getAllSongsSortedByReleaseYear (String searchWord){
        List<Song>songsSortedByReleaseYear=getListToSortElementsWithSearchWord(searchWord);
        songsSortedByReleaseYear.sort(Comparator.comparing(Song::getReleaseYear));
        return songsSortedByReleaseYear;
    }

    public List<Song> getAllSongsSortedByReleaseYearReversed (String searchWord){
        List<Song>songsSortedByReleaseYearReversed=getListToSortElementsWithSearchWord(searchWord);
        songsSortedByReleaseYearReversed.sort(Comparator.comparing(Song::getReleaseYear).reversed());
        return songsSortedByReleaseYearReversed;
    }

    public List<Song> getAllSongsSortedByAuthorName (String searchWord){
        List<Song>songsSortedByAuthorName=getListToSortElementsWithSearchWord(searchWord);
        songsSortedByAuthorName.sort(Comparator.comparing(song -> song.getAuthor().getName()));
        return songsSortedByAuthorName;
    }

    public List<Song> getAllSongsSortedByAuthorNameReversed (String searchWord){
        List<Song>songsSortedByAuthorNameReversed=getListToSortElementsWithSearchWord(searchWord);
        songsSortedByAuthorNameReversed.sort((o1, o2) -> o2.getAuthor().getName().compareTo(o1.getAuthor().getName()));
        return songsSortedByAuthorNameReversed;
    }
}
