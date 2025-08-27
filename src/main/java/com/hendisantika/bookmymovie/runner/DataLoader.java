package com.hendisantika.bookmymovie.runner;

import com.hendisantika.bookmymovie.entity.Movie;
import com.hendisantika.bookmymovie.entity.Screen;
import com.hendisantika.bookmymovie.entity.Screening;
import com.hendisantika.bookmymovie.repository.MovieRepository;
import com.hendisantika.bookmymovie.repository.ScreenRepository;
import com.hendisantika.bookmymovie.repository.ScreeningRepository;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/04/20
 * Time: 01.08
 */
// @Component - Disabled to use Flyway migrations instead
public class DataLoader implements ApplicationRunner {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final ScreeningRepository screeningRepository;
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
        private final String movieLine;
        private final String linkLine;

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
            movie.setMovieId(Integer.parseInt(movieInfo[0]));
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
                Elements posterElements = movieLensPage.getElementsByClass("poster");
                if (posterElements.first() != null && posterElements.first().children().first() != null
                        && posterElements.first().children().first().children().first() != null) {
                    Element image = posterElements.first().children().first().children().first();
                    movie.setMoviePosterUrl(image.attr("src"));
                } else {
                    // Fallback - set a default poster URL or leave it null
                    movie.setMoviePosterUrl("https://via.placeholder.com/300x400?text=No+Poster");
                }
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

    private void populateScreeningsTable() throws CloneNotSupportedException {
        /* Generate screenings for each screen in each theater */
        for (int theatreId = 1; theatreId <= 5; theatreId++) {
            List<Screen> screens = screenRepository.findByTheatreId(theatreId);
            for (Screen screen : screens) {

                // Get random movies
                long totalMovies = movieRepository.count();
                Random random = new Random();

                int movieId1 = random.nextInt((int) totalMovies) + 1;
                Movie movie1 = null;
                while ((movie1 = movieRepository.findByMovieId(movieId1)) == null)
                    movieId1 = random.nextInt((int) totalMovies) + 1;

                int movieId2 = random.nextInt((int) totalMovies) + 1;
                Movie movie2 = null;
                while ((movie2 = movieRepository.findByMovieId(movieId2)) == null)
                    movieId2 = random.nextInt((int) totalMovies) + 1;

                // Get random dates
                Date today = new Date((new java.util.Date()).getTime());
                Date threeDaysLater = new Date(today.getTime() + 3 * 24 * 60 * 60 * 1000);
                Date randomDate1 = new Date(ThreadLocalRandom.current().nextLong(today.getTime(), threeDaysLater.getTime()));
                Date randomDate2 = new Date(ThreadLocalRandom.current().nextLong(today.getTime(), threeDaysLater.getTime()));

                // Create and save first screening at 10:00
                saveScreeningIfNotExists(theatreId, screen.getScreenId(), movie1.getMovieId(), randomDate1, Time.valueOf("10:00:00"));

                // Create and save second screening at 18:00 (same screen, date, theatre but different time)
                saveScreeningIfNotExists(theatreId, screen.getScreenId(), movie1.getMovieId(), randomDate1, Time.valueOf("18:00:00"));

                // Create screenings for second movie only if different date
                if (randomDate1.getTime() != randomDate2.getTime()) {
                    saveScreeningIfNotExists(theatreId, screen.getScreenId(), movie2.getMovieId(), randomDate2, Time.valueOf("10:00:00"));
                    saveScreeningIfNotExists(theatreId, screen.getScreenId(), movie2.getMovieId(), randomDate2, Time.valueOf("18:00:00"));
                }
            }
        }
    }

    private void saveScreeningIfNotExists(long theatreId, long screenId, int movieId, Date date, Time time) {
        // Check if screening already exists
        Screening existing = screeningRepository.findByMovieIdAndTheatreIdAndScreeningDateAndScreeningTime(
                movieId, theatreId, date, time);

        if (existing == null) {
            Screening screening = new Screening();
            screening.setTheatreId(theatreId);
            screening.setScreenId(screenId);
            screening.setMovieId(movieId);
            screening.setScreeningDate(date);
            screening.setScreeningTime(time);
            screening.setBookedTickets(0);
            screeningRepository.save(screening);
        }
    }


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        populateMovieTable();
        populateScreeningsTable();
    }

}
