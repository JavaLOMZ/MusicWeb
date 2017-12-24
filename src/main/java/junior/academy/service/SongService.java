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
}
