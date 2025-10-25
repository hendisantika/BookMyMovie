package com.hendisantika.bookmymovie.controller;

import com.hendisantika.bookmymovie.business.domain.MovieScreening;
import com.hendisantika.bookmymovie.entity.Movie;
import com.hendisantika.bookmymovie.repository.MovieRepository;
import com.hendisantika.bookmymovie.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/04/20
 * Time: 01.21
 */
@Slf4j
@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ScreeningService screeningService;
    private final MovieRepository movieRepository;

    @GetMapping
    public String getMovies(@RequestParam(value = "date", required = false) String dateString, Model model) {
        Date date = null;
        if (dateString != null) {
            try {
                date = DATE_FORMAT.parse(dateString);

            } catch (ParseException pe) {
                date = new Date();
            }
        } else {
            date = new Date();
        }

        Set<Movie> result = this.screeningService.getMoviesByDate(date);
        model.addAttribute("movies", result);
        model.addAttribute("movieBooking", new MovieScreening());
        return "movies";
    }

    @GetMapping("/all")
    public String getAllMovies(Model model) {
        LOGGER.info("Fetching all movies from database");

        // Get all movies and convert to list, sorted by name
        List<Movie> allMovies = StreamSupport
                .stream(movieRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(Movie::getMovieName))
                .collect(Collectors.toList());

        LOGGER.info("Found {} movies in total", allMovies.size());

        model.addAttribute("movies", allMovies);
        model.addAttribute("totalMovies", allMovies.size());

        return "movies-all";
    }
}
