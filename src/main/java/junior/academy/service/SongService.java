package junior.academy.service;

import junior.academy.dao.SongDao;
import junior.academy.domain.MusicGenre;
import junior.academy.domain.Rate;
import junior.academy.domain.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    //    public Set<Song> getRandomSongsByUserPreferences(long userId) {
//        Map<String, Integer> songsSplitBetweenMusicGenre = calculatingUserPreferences(userId);
//        List<Song>allNotRatedSongs=getNotRatedSongs(userId);
//
//        Set<Song> randomSongs = new HashSet<>();
//        for (String name : songsSplitBetweenMusicGenre.keySet()) {
//
//            List<Song> notRatedSongsOfMusicGenre = getNotRatedSongs(userId, name);
//
//            for (int i = 0; i < songsSplitBetweenMusicGenre.get(name) ; i++) {
//                if(notRatedSongsOfMusicGenre.size() != 0) {
//                    Collections.shuffle(notRatedSongsOfMusicGenre);
//                    randomSongs.add(notRatedSongsOfMusicGenre.get(0));
//                    notRatedSongsOfMusicGenre.remove(0);
//                }
//                else if(allNotRatedSongs.size()!=0){
//                    Collections.shuffle(allNotRatedSongs);
//                    randomSongs.add(allNotRatedSongs.get(0));
//                    allNotRatedSongs.remove(0);
//                }
//            }
//        }
//        return randomSongs;
//    }
//
    private List<Song> getNotRatedSongs(long userId, String musicGenre) {
        return songDao.getNotRatedSongs(userId, MusicGenre.valueOf(musicGenre));
    }

    //
//    private List<Song> getNotRatedSongs(long userId) {
//        return songDao.getNotRatedSongs(userId);
//    }
//
//    private Map<String, Integer> calculatingUserPreferences(long userId) {
//        Map<String, Double> sumOfRatesValuePerMusicGenre = getSumOfRatesValuePerMusicGenre(userId);
//        int totalValueOfRates = getTotalValueOfRates(sumOfRatesValuePerMusicGenre);
//
//        Map<String, Integer> songsSplitBetweenMusicGenre = new HashMap<>();
//        for (String name : sumOfRatesValuePerMusicGenre.keySet()) {
//            Long balancedNumber = Math.round((sumOfRatesValuePerMusicGenre.get(name) / totalValueOfRates) * NUMBER_OF_RECOMMENDED_SONGS);
//            songsSplitBetweenMusicGenre.put(name, balancedNumber.intValue());
//        }
//        return songsSplitBetweenMusicGenre;
//    }
//
//    private Map<String, Double> getSumOfRatesValuePerMusicGenre(long userId) {
//        List<Song> ratedSongs = getRatedSongs(userId);
//        Map<Long, Integer> mapOfRates = rateService.getSongAndRateValue(userId);
//        Set<String> ratedMusicGenres = getRatedMusicGenres(ratedSongs);
//
//        Map<String, Double> sumRatesValuePerMusicGenre = new HashMap<>();
//        for (String name : ratedMusicGenres) {
//            int numberOfSongsForMusicType = 0;
//            double sumOfRatesForMusicType = 0;
//            for (Song song : ratedSongs) {
//                if (song.getMusicGenre().name().equals(name)) {
//                    numberOfSongsForMusicType++;
//                    sumOfRatesForMusicType += mapOfRates.get(song.getSongId());
//                }
//            }
//            sumRatesValuePerMusicGenre.put(name, sumOfRatesForMusicType * numberOfSongsForMusicType);
//        }
//        return sumRatesValuePerMusicGenre;
//    }
//
    private List<Song> getRatedSongs(long userId) {
        return songDao.getRatedSongs(userId);
    }
//
//    private Set<String> getRatedMusicGenres(List<Song> ratedSongs) {
//        Set<String> ratedMusicGenres = new HashSet<>();
//        for (Song s : ratedSongs) {
//            ratedMusicGenres.add(s.getMusicGenre().name());
//        }
//        return ratedMusicGenres;
//    }
//
//    private int getTotalValueOfRates(Map<String, Double> sumOfRatesValuePerMusicGenre) {
//        int totalValueOfRates = 0;
//        for (String name : sumOfRatesValuePerMusicGenre.keySet()) {
//            totalValueOfRates += sumOfRatesValuePerMusicGenre.get(name);
//        }
//        return totalValueOfRates;
//    }

    //MY METHOD FOR RANDOM SONGS

    private Map<MusicGenre, List<Song>> getRatedSongsByGenre(long userId) {
        return this.getRatedSongs(userId).stream().collect(Collectors.groupingBy(Song::getMusicGenre));
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

    public List<Song> getRandomSongs(long userId) {
        Map<MusicGenre, Integer> preferenceMap = this.getPreferenceRateByMusicGenres(userId);
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

    //METHOD FOR MOST RATED MUSIC GENRE
    public MusicGenre getMostRatedMusicGenre(long userId) {
        return this.getRatedSongs(userId).stream()
                .collect(Collectors.groupingBy(Song::getMusicGenre, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
    }
//        ArrayList<MusicGenre> musicGenres = this.getMusicGenreTypes();
//        long maxGenreOccurrences = 0;
//        long currentGenreOccurrences = 0;
//        MusicGenre mostRatedMusicGenre = null;
//        for(MusicGenre musicGenre: musicGenres){
//           currentGenreOccurrences = songDao.getRatedSongsByUserId(userId).stream().filter(song -> song.getMusicGenre().equals(musicGenre)).count();
//            System.out.println("CURRENT MUSIC GENRE: "+ musicGenre);
//            System.out.println("MAX GENRE OCCURENCES: " +maxGenreOccurrences + mostRatedMusicGenre);
//           if(currentGenreOccurrences > maxGenreOccurrences){
//               maxGenreOccurrences = currentGenreOccurrences;
//               mostRatedMusicGenre = musicGenre;
//           }
//        }
//        return mostRatedMusicGenre;
//    }
}
