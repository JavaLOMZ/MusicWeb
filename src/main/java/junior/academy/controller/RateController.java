package junior.academy.controller;

import junior.academy.domain.Rate;
import junior.academy.domain.Rate;
import junior.academy.service.RateService;
import junior.academy.service.SongService;
import junior.academy.service.UserService;
import junior.academy.validator.RateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    RateValidator rateValidator;

    @InitBinder()
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(this.rateValidator);
    }

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
    public void createOrUpdateRate(@RequestBody @Valid Rate rate) {
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
    public List<Rate> getRatesByUserId(@PathVariable long userId){
            return rateService.getRatesByUserId(userId);

    }

    @GetMapping("/user/nickname/{nickname}")
    public List<Rate> getRatesByUserNickname(@PathVariable String nickname) {
          return rateService.getRatesByUsername(nickname);
    }

    @GetMapping("/song/{songId}")
    public List<Rate> getRatesBySongId(@PathVariable long songId){
          return rateService.getRatesBySongId(songId);
    }

    @GetMapping("/{userId}/{songId}")
    public ResponseEntity<Rate> getRateForUserAndSong(@PathVariable long userId,@PathVariable long songId){
        Optional<Rate> responseRate = rateService.getRateForUserAndSong(userId, songId);
        return responseRate.map(rate -> new ResponseEntity<>(rate, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
