package com.hendisantika.bookmymovie.repository;

import com.hendisantika.bookmymovie.entity.Seat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 27/08/25
 * Time: 12.00
 */
public interface SeatRepository extends CrudRepository<Seat, Long> {
    List<Seat> findByScreenId(long screenId);
}
