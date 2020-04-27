package com.hendisantika.bookmymovie.repository;

import com.hendisantika.bookmymovie.entity.Theatre;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/04/20
 * Time: 04.39
 */
public interface TheatreRepository extends CrudRepository<Theatre, Long> {
    Theatre findByTheatreId(Long theatreId);

    Theatre findByTheatreNameAndTheatreCity(String theatreName, String theatreCity);
}
