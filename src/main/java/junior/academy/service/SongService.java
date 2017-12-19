package junior.academy.service;

import junior.academy.dao.SongDao;
import junior.academy.domain.MusicGenre;
import junior.academy.domain.Rate;
import junior.academy.domain.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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

    public boolean isSongPresent(long songId) {
        return songDao.getSongById(songId).isPresent();
    }

    public List<Song> getSongsByAuthorId(long authorId) {
        return songDao.getSongsByAuthorId(authorId);
    }

    public ArrayList<MusicGenre> getMusicGenreTypes() {
        return new ArrayList<>(Arrays.asList(MusicGenre.values()));
    }

    public List<Song> getNotRatedSongs(long userId, String musicGenre) {
        return songDao.getNotRatedSongs(userId, MusicGenre.valueOf(musicGenre));
    }

    public List<Song> getRatedSongs(long userId) {
        return songDao.getRatedSongs(userId);
    }

    public Song findSongByNameAndAuthor(String songName, long authorId){
        return songDao.findSongByNameAndAuthor(songName,authorId);
    }

    public Song findSongByName(String songName){
        return songDao.findUniqueSongByName(songName);
    }

    public List<Song> getSongBySearchWord(String searchWord){
        return songDao.getSongBySearchWord(searchWord);
    }


    //is it used anywhere?
    public MusicGenre getMostRatedMusicGenre(long userId) {
        return this.getRatedSongs(userId).stream()
                .collect(Collectors.groupingBy(Song::getMusicGenre, Collectors.counting()))
                .entrySet().stream()
                .max(Entry.comparingByValue())
                .map(Entry::getKey).orElse(null);
    }
}
