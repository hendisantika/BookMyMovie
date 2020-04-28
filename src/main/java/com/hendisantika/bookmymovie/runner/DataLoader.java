package com.hendisantika.bookmymovie.runner;

import com.hendisantika.bookmymovie.entity.Movie;
import com.hendisantika.bookmymovie.repository.MovieRepository;
import com.hendisantika.bookmymovie.repository.ScreenRepository;
import com.hendisantika.bookmymovie.repository.ScreeningRepository;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/04/20
 * Time: 01.08
 */
@Component
public class DataLoader implements ApplicationRunner {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private MovieRepository movieRepository;
    private ScreenRepository screenRepository;
    private ScreeningRepository screeningRepository;
    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    public DataLoader(MovieRepository movieRepository, ScreeningRepository screeningRepository,
                      ScreenRepository screenRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.screenRepository = screenRepository;
    }

    public MovieRepository getMovieRepository() {
        return movieRepository;
    }

    public ScreeningRepository getScreeningRepository() {
        return screeningRepository;
    }

    public ScreenRepository getScreenRepository() {
        return screenRepository;
    }

    private class ProcessMovie implements Runnable {
        private String movieLine;
        private String linkLine;

        ProcessMovie(String movieLine, String linkLine) {
            this.movieLine = movieLine;
            this.linkLine = linkLine;
        }

        @Override
        public void run() {
            LOGGER.info(Thread.currentThread().getId() + ":" + linkLine);
            String[] movieInfo = movieLine.split(",");

            String movieName = "";

            for (int i = 1; i < movieInfo.length - 1; i++) {
                if (i == movieInfo.length - 2)
                    movieName += movieInfo[i];
                else
                    movieName += movieInfo[i] + ",";
            }

            Movie movie = new Movie();
            movie.setMovieId(Long.parseLong(movieInfo[0]));
            movie.setMovieName(movieName.substring(0, movieName.indexOf('(')).trim());
            movie.setMovieTags(movieInfo[2]);

            String[] linkInfo = linkLine.split(",");
            Document movieLensPage = null;
            try {
                movieLensPage = Jsoup.connect("https://www.imdb.com/title/tt" + linkInfo[1]).get();
            } catch (HttpStatusException e) {
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (movieLensPage != null) {
                Element image =
                        movieLensPage.getElementsByClass("poster").first().children().first().children().first();
                movie.setMoviePosterUrl(image.attr("src"));
            }

            movieRepository.save(movie);
        }
    }

    private void populateMovieTable() {
        try (BufferedReader brMovies = new BufferedReader(new InputStreamReader(new ClassPathResource("movies.medium" +
                ".csv").getInputStream()));
             BufferedReader brLinks =
                     new BufferedReader(new InputStreamReader(new ClassPathResource("links.csv").getInputStream()))) {
            String movieLine;
            String linkLine;
            brMovies.readLine();    // Skip header line
            brLinks.readLine();     // Skip header line
            while ((movieLine = brMovies.readLine()) != null) {
                linkLine = brLinks.readLine();
                //taskExecutor.execute(new ProcessMovie(movieLine, linkLine));
                new ProcessMovie(movieLine, linkLine).run();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
