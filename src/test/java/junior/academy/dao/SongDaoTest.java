package junior.academy.dao;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SongDaoTest extends EntityDaoTest {

    @Autowired
    SongDao songDao;

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet[] dataSets = new IDataSet[]{
                new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Song.xml"))
        };
        return new CompositeDataSet(dataSets);
    }

    @Test
    public void getAllSongs() {
        assertEquals(songDao.getAllSongs().size(), 1);
    }

}
