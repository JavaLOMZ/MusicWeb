package junior.academy.service;


import junior.academy.dao.RateDao;
import junior.academy.domain.Rate;

import static org.junit.Assert.*;

import org.mockito.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class RateServiceTest {
    @Mock
    RateDao rateDao;

    @InjectMocks
    RateService rateService;

    @Spy
    List<Rate> rates = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        rates = getRateList();
    }

    @Test
    public void createOrUpdateRate() {
        doNothing().when(rateDao).createOrUpdateRate((any(Rate.class)));
        rateService.createOrUpdateRate(any(Rate.class));
        verify(rateDao, atLeastOnce()).createOrUpdateRate(any(Rate.class));
    }

    @Test
    public void getAllRates() {
        when(rateDao.getAllRates()).thenReturn(rates);
        assertEquals(rateService.getAllRates(), rates);
    }

    @Test
    public void deleteRateById() {
        doNothing().when(rateDao).deleteRateById(anyLong());
        rateService.deleteRateById(anyLong());
        verify(rateDao, atLeastOnce()).deleteRateById(anyLong());
    }

    @Test
    public void getRateById() {
        Rate rate = rates.get(0);
        when(rateDao.getRateById(anyLong())).thenReturn(Optional.ofNullable(rate));
        assertEquals(rateService.getRateById(anyLong()), Optional.of(rate));
    }

    private List<Rate> getRateList() {
        Rate rate = new Rate();

        rates.add(rate);
        return rates;
    }
}
