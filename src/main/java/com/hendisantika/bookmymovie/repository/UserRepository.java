package com.hendisantika.bookmymovie.repository;

import com.hendisantika.bookmymovie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/04/20
 * Time: 04.41
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
