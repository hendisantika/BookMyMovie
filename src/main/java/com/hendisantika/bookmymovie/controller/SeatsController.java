package com.hendisantika.bookmymovie.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/04/20
 * Time: 01.23
 */
@Controller
@RequestMapping("/seats")
public class SeatsController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String bookSeats(@RequestParam(value = "count", required = false) Integer seatCount,
                           @RequestParam(value = "movieName", required = false) String movieName,
                           @RequestParam(value = "moviePoster", required = false) String moviePoster,
                           @RequestParam(value = "theatreName", required = false) String theatreName,
                           @RequestParam(value = "theatreCity", required = false) String theatreCity,
                           @RequestParam(value = "screeningDate", required = false) String screeningDate,
                           @RequestParam(value = "screeningTime", required = false) String screeningTime,
                           Model model) {

        // Add movie and screening details to the model
        model.addAttribute("count", seatCount != null ? seatCount : 0);
        model.addAttribute("movieName", movieName);
        model.addAttribute("moviePoster", moviePoster);
        model.addAttribute("theatreName", theatreName);
        model.addAttribute("theatreCity", theatreCity);
        model.addAttribute("screeningDate", screeningDate);
        model.addAttribute("screeningTime", screeningTime);

        LOGGER.info("Booking seats for movie: {}, theatre: {}, date: {}, time: {}",
                   movieName, theatreName, screeningDate, screeningTime);

        return "seats";
    }
}
