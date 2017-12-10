package junior.academy.controller;

import junior.academy.controller.RateController;
import junior.academy.domain.Rate;
import junior.academy.service.RateService;
import junior.academy.service.SongService;
import junior.academy.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RateControllerTest {

    @Mock
    RateService rateService;

    @Mock
    SongService songService;

    @InjectMocks
    RateController rateController;

    @Spy
    List<Rate> rates = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        rates = getRateList();
    }

    @Test
    public void getRateWhenPresent(){
        Rate testRate = rates.get(0);
        when(rateService.isRatePresent(anyLong())).thenReturn(true);
        when(rateService.getRateById(anyLong())).thenReturn(java.util.Optional.ofNullable(testRate));
        assertEquals(rateController.getRate(anyLong()), new ResponseEntity<>(testRate, HttpStatus.OK));
    }

    @Test
    public void getRateWhenNotPresent(){
        when(rateService.isRatePresent(anyLong())).thenReturn(false);
        assertEquals(rateController.getRate(anyLong()), new ResponseEntity<>(any(Rate.class), HttpStatus.NOT_FOUND));
    }

    @Test
    public void getAllRates() {
        when(rateService.getAllRates()).thenReturn(rates);
        assertEquals(rateController.getAllRates(), rates);
    }

    @Test
    public void createOrUpdateRate(){
        doNothing().when(rateService).createOrUpdateRate((any(Rate.class)));
        rateController.createOrUpdateRate(any(Rate.class));
        verify(rateService, atLeastOnce()).createOrUpdateRate(any(Rate.class));
    }

    @Test
    public void deleteRateWhenPresent() {
        when(rateService.isRatePresent(anyLong())).thenReturn(true);
        doNothing().when(rateService).deleteRateById(anyLong());
        assertEquals(rateController.deleteRateById(anyLong()), new ResponseEntity(HttpStatus.OK));
        verify(rateService, atLeastOnce()).deleteRateById(anyLong());
    }

    @Test
    public void deleteRateWhenNotPresent() {
        when(rateService.isRatePresent(anyLong())).thenReturn(false);
        assertEquals(rateController.deleteRateById(anyLong()), new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @Test
    public void getRatesByUserIdTest(){
        List<Rate> testRatesList = new ArrayList<>();
        when(rateService.getRatesByUserId(anyLong())).thenReturn(testRatesList);
        assertEquals(rateController.getRatesByUserId(anyLong()), testRatesList);
    }

    @Test
    public void getRatesByUsernameTest(){
        List<Rate> testRatesList = new ArrayList<>();
        when(rateService.getRatesByUsername(anyString())).thenReturn(testRatesList);
        assertEquals(rateController.getRatesByUserNickname(anyString()), testRatesList);
    }

    @Test
    public void getRatesBySongIdTest(){
        List<Rate> testRatesList = new ArrayList<>();
        when(rateService.getRatesBySongId(anyLong())).thenReturn(testRatesList);
        assertEquals(rateController.getRatesBySongId(anyLong()), testRatesList);
    }

    @Test
    public void getRateForUserAndSongWhenBothPresentTest(){
        Rate testRate = new Rate();
        when(rateService.getRateForUserAndSong(anyLong(), anyLong())).thenReturn(Optional.of(testRate));
        assertEquals(rateController.getRateForUserAndSong(anyLong(), anyLong()), new ResponseEntity<>(testRate, HttpStatus.OK));
    }

    @Test
    public void getRateForUserAndSongWhenNotPresentTest(){
        Rate testRate = new Rate();
        when(rateService.getRateForUserAndSong(anyLong(), anyLong())).thenReturn(Optional.of(testRate));
        when(rateService.isRatePresent(anyLong())).thenReturn(false);
        assertEquals(rateController.getRateForUserAndSong(anyLong(), anyLong()), new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private List<Rate> getRateList() {
        Rate rate = new Rate();

        rates.add(rate);
        return rates;
    }
}
