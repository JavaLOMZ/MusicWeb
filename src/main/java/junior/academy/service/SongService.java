package junior.academy.service;

import junior.academy.dao.SongDao;
import junior.academy.domain.MusicGenre;
import junior.academy.domain.Rate;
import junior.academy.domain.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        String watchYoutubeLink=song.getYouTubeLink();
        song.setYouTubeLink(watchYoutubeLink.replace("https://www.youtube.com/watch?v=","https://www.youtube.com/embed/"));
        songDao.createOrUpdateSong(song);
    }

    public void deleteSongById(long songId) {
        songDao.deleteSongById(songId);
    }

    public boolean isSongPresent(long songId) {
        return songDao.getSongById(songId).isPresent();
    }

    public List<Song> getSongsByAuthorId(long authorId){
        return songDao.getSongsByAuthorId(authorId);
    }

    public ArrayList<MusicGenre> getMusicGenreTypes(){
        return new ArrayList<>(Arrays.asList(MusicGenre.values()));
    }

    public List<Song> getNotRatedSongsByUserId(long userId){
        return songDao.getNotRatedSongsByUserId(userId);
    }

    public List<Song> getRatedSongsByUserId(long userId){
        return songDao.getRatedSongsByUserId(userId);
    }

    public void findingWhatUserLikes(long userId){
        List<Song> ratedSongs=getRatedSongsByUserId(userId);
        for(Song s:ratedSongs){
            System.out.println(s.getRates());
        }

    }
}
