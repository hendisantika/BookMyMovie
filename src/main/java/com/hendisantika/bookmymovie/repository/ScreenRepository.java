package com.hendisantika.bookmymovie.repository;

import com.hendisantika.bookmymovie.entity.Screen;
import org.springframework.data.repository.CrudRepository;

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
public interface ScreenRepository extends CrudRepository<Screen, Long> {
    public Screen findByScreenId(long screenId);

    public List<Screen> findByTheatreId(long theatreId);
}
