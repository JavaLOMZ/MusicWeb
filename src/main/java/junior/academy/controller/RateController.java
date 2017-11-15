package junior.academy.controller;

import junior.academy.domain.Rate;
import junior.academy.domain.Rate;
import junior.academy.service.RateService;
import junior.academy.service.SongService;
import junior.academy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rate")
public class RateController {

    @Autowired
    RateService rateService;

    @Autowired
    UserService userService;

    @Autowired
    SongService songService;

    @GetMapping("/{rateId}")
    public ResponseEntity<Rate> getRate(@PathVariable long rateId) {
        if (rateService.isRatePresent(rateId)) {
            return new ResponseEntity<>(rateService.getRateById(rateId).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<Rate> getAllRates() {
        return rateService.getAllRates();
    }

    @PostMapping
    public void createOrUpdateRate(@RequestBody Rate rate) {
        rateService.createOrUpdateRate(rate);
    }

    @DeleteMapping("/{rateId}")
    public ResponseEntity deleteRateById(@PathVariable long rateId) {
        if (rateService.isRatePresent(rateId)) {
            rateService.deleteRateById(rateId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rate>> getRatesByUserId(@PathVariable long userId){
        if(userService.isUserPresent(userId)){
            return new ResponseEntity<>(rateService.getRatesByUserId(userId),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/nickname/{nickname}")
    public ResponseEntity<List<Rate>> getRatesByUserNickname(@PathVariable String nickname){
        if(userService.isUserPresent(nickname)){
            return new ResponseEntity<>(rateService.getRatesByUsername(nickname),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/song/{songId}")
    public ResponseEntity<List<Rate>> getRatesBySongId(@PathVariable long songId){
        if(songService.isSongPresent(songId)){
            return new ResponseEntity<>(rateService.getRatesBySongId(songId),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userId}/{songId}")
    public ResponseEntity<Rate> getRateForUserAndSong(@PathVariable long userId,@PathVariable long songId){
        if(rateService.getRateForUserAndSong(userId,songId).isPresent()){
            return new ResponseEntity<>(rateService.getRateForUserAndSong(userId,songId).get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
