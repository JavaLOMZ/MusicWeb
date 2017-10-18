package junior.academy.controller;

import junior.academy.domain.Rate;
import junior.academy.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rate")
public class RateController {

    @Autowired
    RateService rateService;

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
}
