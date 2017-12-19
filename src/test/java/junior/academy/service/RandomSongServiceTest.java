package junior.academy.service;


import junior.academy.domain.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class RandomSongServiceTest {

    private static final int NUMBER_OF_RECOMMENDED_SONGS = 5;

    @Mock
    SongService songService;

    @InjectMocks
    RandomSongService randomSongService;

    @Spy
    List<Song> songs=new ArrayList<>();

    @BeforeClass
    public void setUp(){
        songs=getSongList();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getRandomSongs(){
        long userId=0;
        when(songService.getRatedSongs(userId)).thenReturn(songs);
        Map<MusicGenre, Integer> unsortedMap=randomSongService.getPreferenceRateByMusicGenres(userId);
        Map<MusicGenre,Integer> preferenceMap=randomSongService.getMusicGenrePreferenceSorted(unsortedMap);
        List<Song> randomSongs = new ArrayList<>();
        double totalPreferenceValue = preferenceMap.values().stream().mapToInt(integer -> integer).sum();
        for (MusicGenre musicGenre : preferenceMap.keySet()) {
            List<Song>musicGenreUnratedSongs=songsForMusicGenre(musicGenre);
            when(songService.getNotRatedSongs(userId,String.valueOf(musicGenre))).thenReturn(musicGenreUnratedSongs);
            List<Song> currentGenreNotRatedSongList = songService.getNotRatedSongs(userId, String.valueOf(musicGenre));
            Collections.shuffle(currentGenreNotRatedSongList);
            double numberOfSongs = (preferenceMap.get(musicGenre).doubleValue() / totalPreferenceValue * NUMBER_OF_RECOMMENDED_SONGS);
            for (int i = 0; i < numberOfSongs && randomSongs.size() < NUMBER_OF_RECOMMENDED_SONGS && currentGenreNotRatedSongList.size() > i; i++) {
                randomSongs.add(currentGenreNotRatedSongList.get(i));
            }
        }
        assertEquals(randomSongService.getRandomSongs(userId),randomSongs);
    }

    @Test
    public void getMusicGenrePreferenceSorted(){
        Map<MusicGenre,Integer> map=getPreferenceMapOfMusic();
        Map<MusicGenre,Integer>sortedMap=map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        assertEquals(randomSongService.getMusicGenrePreferenceSorted(map),sortedMap);
    }


    @Test
    public void getPreferenceRateByMusicGenres(){
        long userId=0;
        when(songService.getRatedSongs(userId)).thenReturn(songs);
        Map<MusicGenre,List<Song>> ratedSongsByGenre=randomSongService.getRatedSongsByGenre(anyLong());
        Map<MusicGenre, Integer> preferenceMap = new HashMap<>();
        for (MusicGenre musicGenre : ratedSongsByGenre.keySet()) {
            int numberOfSongsTimesSumOfRates = ratedSongsByGenre.get(musicGenre).stream()
                    .mapToInt(song -> song.getRates().stream()
                            .filter(rate -> rate.getUser().getUserId() == userId)
                            .mapToInt(Rate::getRateValue).sum()).sum() * ratedSongsByGenre.get(musicGenre).size();
            preferenceMap.put(musicGenre, numberOfSongsTimesSumOfRates);
        }
        assertEquals(randomSongService.getPreferenceRateByMusicGenres(userId),preferenceMap);
    }

    @Test
    public void getRatedSongsByGenre(){
        when(songService.getRatedSongs(anyLong())).thenReturn(songs);
        Map<MusicGenre,List<Song>> map=songs.stream().collect(Collectors.groupingBy(Song::getMusicGenre));
        assertEquals(randomSongService.getRatedSongsByGenre(anyLong()),map);
    }

    List<Song> getSongList(){
        Song song=new Song();
        song.setSongName("testSong");
        song.setAuthor(new Author());
        song.setMusicGenre(MusicGenre.HIPHOP);
        song.setReleaseYear(1900);
        song.setYouTubeLink("www.youtube.com/test2");
        song.setRates(getRates());
        songs.add(song);
        songs.add(song);
        Song song2=new Song();
        song2.setSongName("testSong2");
        song2.setAuthor(new Author());
        song2.setMusicGenre(MusicGenre.ROCK);
        song2.setReleaseYear(1900);
        song2.setYouTubeLink("www.youtube.com/test22");
        song2.setRates(getRates());
        songs.add(song2);
        return songs;
    }

    List<Song>songsForMusicGenre(MusicGenre musicGenre){
        List<Song> songForMusicGenre=new ArrayList<>();
        for(Song s:songs){
            if(s.getMusicGenre().equals(musicGenre)){
                songForMusicGenre.add(s);
            }
        }
        return songForMusicGenre;
    }

    Set<Rate> getRates(){
        Set<Rate>rates=new HashSet<>();
        Rate rate= new Rate();
        rate.setUser(new User());
        rate.setRateValue(10);
        rates.add(rate);
        return rates;
    }

    Map<MusicGenre,Integer> getPreferenceMapOfMusic(){
        Map<MusicGenre,Integer> map=new HashMap<>();
        map.put(MusicGenre.ROCK,10);
        map.put(MusicGenre.HIPHOP,20);
        return map;
    }

}
