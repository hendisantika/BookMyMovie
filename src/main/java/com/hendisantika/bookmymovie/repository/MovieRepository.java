package com.hendisantika.bookmymovie.repository;

import com.hendisantika.bookmymovie.entity.Movie;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/04/20
 * Time: 04.37
 */
public interface MovieRepository extends CrudRepository<Movie, String> {
    Movie findByMovieName(String movieName);

    Movie findByMovieId(long movieId);
}
