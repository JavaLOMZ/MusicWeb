package junior.academy.service;

import junior.academy.dao.SongDao;
import junior.academy.domain.MusicGenre;
import junior.academy.domain.Rate;
import junior.academy.domain.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EnumType;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SongService {

    private static final int AMOUNT_OF_SONGS_ON_RECOMMENDATION_PAGE = 9;

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


    public Set<Song> getRandomSongsByUserPreferences(long userId) {
        Map<String, Integer> songsByMusicGenre = calculatingUserPreferences(userId);
        List<Song>allNotRatedSongs=getNotRatedSongs(userId);

        Set<Song> randomSongs = new HashSet<>();
        for (String name : songsByMusicGenre.keySet()) {

            List<Song> notRatedSongsOfMusicGenre = getNotRatedSongs(userId, name);


            for (int i = 0; i < songsByMusicGenre.get(name) ; i++) {
                if(notRatedSongsOfMusicGenre.size() != 0) {
                    Collections.shuffle(notRatedSongsOfMusicGenre);
                    randomSongs.add(notRatedSongsOfMusicGenre.get(0));
                    notRatedSongsOfMusicGenre.remove(0);
                }

                //todo
                else if(allNotRatedSongs.size()!=0){
                    Collections.shuffle(allNotRatedSongs);
                    randomSongs.add(allNotRatedSongs.get(0));
                    allNotRatedSongs.remove(0);
                }
            }
        }
        return randomSongs;
    }

    private List<Song> getNotRatedSongs(long userId, String musicGenre) {
        return songDao.getNotRatedSongs(userId, MusicGenre.valueOf(musicGenre));
    }

    private List<Song> getNotRatedSongs(long userId) {
        return songDao.getNotRatedSongs(userId);
    }

    private Map<String, Integer> calculatingUserPreferences(long userId) {
        Map<String, Double> sumOfRatesValuePerMusicGenre = getSumOfRatesValuePerMusicGenre(userId);
        int totalValueOfRates = getTotalValueOfRates(sumOfRatesValuePerMusicGenre);
        int songCount=0;
        Map<String, Integer> songsByMusicGenre = new TreeMap<>();
        for (String name : sumOfRatesValuePerMusicGenre.keySet()) {
            Long balancedNumber = Math.round((sumOfRatesValuePerMusicGenre.get(name) / totalValueOfRates) * AMOUNT_OF_SONGS_ON_RECOMMENDATION_PAGE);
            songCount+=balancedNumber;
            songsByMusicGenre.put(name, balancedNumber.intValue());
        }
        if(songCount<AMOUNT_OF_SONGS_ON_RECOMMENDATION_PAGE){
            Map<String, Integer> sortedSongsByMusicGenre =
                    songsByMusicGenre.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));
            List<String> indexes=new ArrayList<>(sortedSongsByMusicGenre.keySet());
            String musicGenreWithBiggestBalanceNumber=indexes.get(indexes.size()-1);
            sortedSongsByMusicGenre.put(musicGenreWithBiggestBalanceNumber,sortedSongsByMusicGenre.get(musicGenreWithBiggestBalanceNumber)+1);
            return sortedSongsByMusicGenre;
        }
        return songsByMusicGenre;
    }

    private Map<String, Double> getSumOfRatesValuePerMusicGenre(long userId) {
        List<Song> ratedSongs = getRatedSongs(userId);
        Map<Long, Integer> mapOfRates = rateService.getSongAndRateValue(userId);
        Set<String> ratedMusicGenres = getRatedMusicGenres(ratedSongs);

        Map<String, Double> sumRatesValuePerMusicGenre = new HashMap<>();
        for (String name : ratedMusicGenres) {
            int numberOfSongsForMusicType = 0;
            double sumOfRatesForMusicType = 0;
            for (Song song : ratedSongs) {
                if (song.getMusicGenre().name().equals(name)) {
                    numberOfSongsForMusicType++;
                    sumOfRatesForMusicType += mapOfRates.get(song.getSongId());
                }
            }
            sumRatesValuePerMusicGenre.put(name, sumOfRatesForMusicType * numberOfSongsForMusicType);
        }
        return sumRatesValuePerMusicGenre;
    }

    private List<Song> getRatedSongs(long userId) {
        return songDao.getRatedSongs(userId);
    }

    private Set<String> getRatedMusicGenres(List<Song> ratedSongs) {
        Set<String> ratedMusicGenres = new HashSet<>();
        for (Song s : ratedSongs) {
            ratedMusicGenres.add(s.getMusicGenre().name());
        }
        return ratedMusicGenres;
    }

    private int getTotalValueOfRates(Map<String, Double> sumOfRatesValuePerMusicGenre) {
        int totalValueOfRates = 0;
        for (String name : sumOfRatesValuePerMusicGenre.keySet()) {
            totalValueOfRates += sumOfRatesValuePerMusicGenre.get(name);
        }
        return totalValueOfRates;
    }

}
