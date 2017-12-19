package junior.academy.service;


import junior.academy.domain.MusicGenre;
import junior.academy.domain.Rate;
import junior.academy.domain.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RandomSongService {
    private static final int NUMBER_OF_RECOMMENDED_SONGS = 5;

    @Autowired
    SongService songService;

    public List<Song> getRandomSongs(long userId) {
        Map<MusicGenre, Integer> preferenceMap = getMusicGenrePreferenceSorted(getPreferenceRateByMusicGenres(userId));
        List<Song> randomSongs = new ArrayList<>();
        double totalPreferenceValue = preferenceMap.values().stream().mapToInt(integer -> integer).sum();
        for (MusicGenre musicGenre : preferenceMap.keySet()) {
            List<Song> currentGenreNotRatedSongList = songService.getNotRatedSongs(userId, String.valueOf(musicGenre));
            Collections.shuffle(currentGenreNotRatedSongList);
            double numberOfSongs = (preferenceMap.get(musicGenre).doubleValue() / totalPreferenceValue * NUMBER_OF_RECOMMENDED_SONGS);
            for (int i = 0; i < numberOfSongs && randomSongs.size() < NUMBER_OF_RECOMMENDED_SONGS && currentGenreNotRatedSongList.size() > i; i++) {
                randomSongs.add(currentGenreNotRatedSongList.get(i));
            }
        }
        return randomSongs;
    }

    public Map<MusicGenre, Integer> getMusicGenrePreferenceSorted(Map<MusicGenre, Integer> unsortedPreferenceMap) {
        return unsortedPreferenceMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    public Map<MusicGenre, Integer> getPreferenceRateByMusicGenres(long userId) {
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

    public Map<MusicGenre, List<Song>> getRatedSongsByGenre(long userId) {
        return songService.getRatedSongs(userId).stream().collect(Collectors.groupingBy(Song::getMusicGenre));
    }
}
