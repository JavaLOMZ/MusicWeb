package junior.academy.service;


import junior.academy.dao.DefaultDao;
import junior.academy.dao.RateDao;
import junior.academy.domain.Rate;

import static org.junit.Assert.*;

import junior.academy.domain.Song;
import org.mockito.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class RateServiceTest {
    @Mock
    RateDao rateDao;

    @Mock
    DefaultDao defaultDao;

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
        doNothing().when(defaultDao).saveOrUpdate((any(Rate.class)));
        rateService.createOrUpdateRate(any(Rate.class));
        verify(defaultDao, atLeastOnce()).saveOrUpdate(any(Rate.class));
    }

    @Test
    public void getAllRates() {
        when(defaultDao.getAll(Rate.class)).thenReturn(rates);
        assertEquals(rateService.getAllRates(), rates);
    }

    @Test
    public void deleteRateById() {
        long id=rates.get(0).getRateId();
        doNothing().when(defaultDao).deleteById(eq(Rate.class),anyLong());
        rateService.deleteRateById(id);
        verify(defaultDao, atLeastOnce()).deleteById(eq(Rate.class),anyLong());
    }

    @Test
    public void getRateById() {
        Rate rate = rates.get(0);
        when(defaultDao.getById(eq(Rate.class),anyLong())).thenReturn(Optional.ofNullable(rate));
        assertEquals(rateService.getRateById(rate.getRateId()), Optional.of(rate));
    }

    @Test
    public void isRatePresent() {
        Rate rate = rates.get(0);
        when(defaultDao.getById(eq(Rate.class),anyLong())).thenReturn(Optional.ofNullable(rate));
        assertEquals(rateService.isRatePresent(rate.getRateId()), true);
    }

    @Test
    public void getRatesByUserId(){
        when(rateDao.getRatesByUserId(anyLong())).thenReturn(rates);
        assertEquals(rateService.getRatesByUserId(anyLong()),rates);
    }

    @Test
    public void getRatedByUsername(){
        when(rateDao.getRatesByUsername(anyString())).thenReturn(rates);
        assertEquals(rateService.getRatesByUsername(anyString()),rates);
    }

    @Test
    public void getRatesBySongId(){
        when(rateDao.getRatesBySongId(anyLong())).thenReturn(rates);
        assertEquals(rateService.getRatesBySongId(anyLong()),rates);
    }

    @Test
    public void songAverageRate(){
        when(rateService.getRatesBySongId(anyLong())).thenReturn(rates);
        double averageSongRate=(rates.stream().mapToDouble(Rate::getRateValue).sum())/rates.size();
        assertEquals(rateService.songAverageRate(anyLong()),averageSongRate,0);
    }

    @Test
    public void getRateForUserAndSong(){
        Rate rate = getRateList().get(0);
        when(rateDao.getRateForUserAndSong(anyLong(),anyLong())).thenReturn(Optional.ofNullable(rate));
        assertEquals(rateService.getRateForUserAndSong(anyLong(),anyLong()),Optional.ofNullable(rate));
    }

    @Test
    public void getSongAndRateValue(){
        when(rateDao.getRatesByUserId(anyLong())).thenReturn(rates);
        List<Rate>rates=rateService.getRatesByUserId(anyLong());
        Map<Long,Integer> mapOfRates=new HashMap<>();
        for(Rate r:rates){
            mapOfRates.put(r.getSong().getSongId(),r.getRateValue());
        }
        assertEquals(rateService.getSongAndRateValue(anyLong()),mapOfRates);
    }

    private List<Rate> getRateList() {
        Rate rate = new Rate();
        rate.setRateValue(10);
        rate.setSong(new Song());
        Rate rate2=new Rate();
        rate2.setRateValue(20);
        rate2.setSong(new Song());
        rates.add(rate);
        rates.add(rate2);
        return rates;
    }
}
