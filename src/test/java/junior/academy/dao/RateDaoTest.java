package junior.academy.dao;

import junior.academy.domain.Rate;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

public class RateDaoTest extends EntityDaoTest {

    @Autowired
    RateDao rateDao;

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet[] datasets = new IDataSet[]{
                new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Rate.xml"))
        };
        return new CompositeDataSet(datasets);
    }

    @Test
    public void getAllRates() {
        assertEquals(rateDao.getAllRates().size(), 1);
    }

    @Test
    public void getRateById() {
        assertNotNull(rateDao.getRateById(1));
        assertTrue(rateDao.getRateById(1).get().getRateValue() == 8);
    }

    @Test
    public void createRate() {
        Rate rate = getRate();
        rateDao.createOrUpdateRate(rate);
        assertEquals(rateDao.getAllRates().size(), 2);
    }

    @Test
    public void updateRate() {
        Rate rateTest = rateDao.getRateById(1).get();
        rateTest.setRateValue(10);
        rateDao.createOrUpdateRate(rateTest);
        assertTrue(rateDao.getRateById(1).get().getRateValue() == 10);
    }

    @Test
    public void deleteRate() {
        rateDao.deleteRateById(1);
        assertEquals(rateDao.getAllRates().size(), 0);
    }

    private Rate getRate() {
        Rate rate = new Rate();

        return rate;
    }
}
