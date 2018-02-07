package junior.academy.dao;

import junior.academy.domain.Author;
import junior.academy.domain.MusicGenre;
import junior.academy.domain.Song;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Matchers.anyLong;
import static org.testng.Assert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SongDaoTest extends EntityDaoTest {

    @Autowired
    SongDao songDao;

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet[] datasets = new IDataSet[]{
                new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Song.xml"))
        };
        return new CompositeDataSet(datasets);
    }

    //todo 4 tests missing

//    @Test
//    public void getSongsByAuthorId(){
//        assertEquals(songDao.getSongsByAuthorId(1).size(),1);
//    }

    private List<Song> getSong() {
        List<Song> songs=new ArrayList<>();
        Song song = new Song();
        song.setSongName("TestSongName");
        song.setMusicGenre(MusicGenre.HIPHOP);
        song.setYouTubeLink("YoutubeLink");
        song.setSongAverageRate(0);
        Author author=new Author();
        author.setAuthorId(1);
        song.setAuthor(author);
        songs.add(song);
        return songs;
    }
}
