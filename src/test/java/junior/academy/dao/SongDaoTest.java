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

import org.testng.annotations.Test;

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

    @Test
    public void getSongsByAuthorId(){
        assertEquals(songDao.getSongsByAuthorId(anyLong()).size(),0);
    }

    private Song getSong() {
        Song song = new Song();
        song.setSongName("TestSongName");
        song.setMusicGenre(MusicGenre.HIPHOP);

        song.setYouTubeLink("YoutubeLink");
        return song;
    }
}
