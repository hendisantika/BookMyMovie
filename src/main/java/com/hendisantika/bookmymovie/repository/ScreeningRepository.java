package com.hendisantika.bookmymovie.repository;

import com.hendisantika.bookmymovie.entity.Screening;
import org.springframework.data.repository.CrudRepository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/04/20
 * Time: 04.38
 */
public interface ScreeningRepository extends CrudRepository<Screening, Long> {
    List<Screening> findByScreeningDate(Date screeningDate);

    List<Screening> findByMovieId(int movieId);

    Screening findByMovieIdAndTheatreIdAndScreeningDateAndScreeningTime(int movieId, long theatreId,
                                                                        Date screeningDate, Time screeningTime);
}