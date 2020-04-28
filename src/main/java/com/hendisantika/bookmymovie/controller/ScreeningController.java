package com.hendisantika.bookmymovie.controller;

import com.hendisantika.bookmymovie.business.domain.MovieScreening;
import com.hendisantika.bookmymovie.repository.MovieRepository;
import com.hendisantika.bookmymovie.service.ScreeningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/04/20
 * Time: 01.22
 */
@Controller
@RequestMapping("/screenings")
public class ScreeningController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningService screeningService;

    @GetMapping
    public String getScreenings(@RequestParam(value = "movie", required = true) String movieString, Model model) {
        List<MovieScreening> result = this.screeningService.getMovieScreeningsByMovie(movieString);
        model.addAttribute("screenings", result);
        model.addAttribute("movie", movieRepository.findByMovieName(movieString));
        return "screenings";
    }
}
