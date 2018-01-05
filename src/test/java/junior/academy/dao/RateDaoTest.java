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

    //todo 4 tests missing

    private Rate getRate() {
        Rate rate = new Rate();

        return rate;
    }
}
