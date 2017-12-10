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

    private static final int NUMBER_OF_RECOMMENDED_SONGS = 5;

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


    public List<Song> getRandomSongs(long userId) {
        Map<MusicGenre, Integer> preferenceMap = getMusicGenrePreferenceSorted(getPreferenceRateByMusicGenres(userId));
        List<Song> randomSongs = new ArrayList<>();
        double totalPreferenceValue = preferenceMap.values().stream().mapToInt(integer -> integer).sum();
        for (MusicGenre musicGenre : preferenceMap.keySet()) {
            List<Song> currentGenreNotRatedSongList = this.getNotRatedSongs(userId, String.valueOf(musicGenre));
            Collections.shuffle(currentGenreNotRatedSongList);
            double numberOfSongs = (preferenceMap.get(musicGenre).doubleValue() / totalPreferenceValue * NUMBER_OF_RECOMMENDED_SONGS);
            for (int i = 0; i < numberOfSongs && randomSongs.size() < NUMBER_OF_RECOMMENDED_SONGS && currentGenreNotRatedSongList.size() > i; i++) {
                randomSongs.add(currentGenreNotRatedSongList.get(i));
            }
        }
        return randomSongs;
    }

    private Map<MusicGenre, Integer> getMusicGenrePreferenceSorted(Map<MusicGenre, Integer> unsortedPreferenceMap) {
        return unsortedPreferenceMap.entrySet().stream()
                .sorted(Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    private List<Song> getNotRatedSongs(long userId, String musicGenre) {
        return songDao.getNotRatedSongs(userId, MusicGenre.valueOf(musicGenre));
    }

    private Map<MusicGenre, Integer> getPreferenceRateByMusicGenres(long userId) {
        Map<MusicGenre, List<Song>> ratedSongsByGenre = this.getRatedSongsByGenre(userId);
        Map<MusicGenre, Integer> preferenceMap = new HashMap<>();
        for (MusicGenre musicGenre : ratedSongsByGenre.keySet()) {
            int numberOfSongsTimesSumOfRates = ratedSongsByGenre.get(musicGenre).stream()
                    .mapToInt(song -> song.getRates().stream()
                            .filter(rate -> rate.getUser().getUserId() == userId)
                            .mapToInt(Rate::getRateValue).sum()).sum() * ratedSongsByGenre.get(musicGenre).size();
            preferenceMap.put(musicGenre, numberOfSongsTimesSumOfRates);
        }
        return preferenceMap;
    }

    private Map<MusicGenre, List<Song>> getRatedSongsByGenre(long userId) {
        return this.getRatedSongs(userId).stream().collect(Collectors.groupingBy(Song::getMusicGenre));
    }

    private List<Song> getRatedSongs(long userId) {
        return songDao.getRatedSongs(userId);
    }


    public MusicGenre getMostRatedMusicGenre(long userId) {
        return this.getRatedSongs(userId).stream()
                .collect(Collectors.groupingBy(Song::getMusicGenre, Collectors.counting()))
                .entrySet().stream()
                .max(Entry.comparingByValue())
                .map(Entry::getKey).orElse(null);
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
}
