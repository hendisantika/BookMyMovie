package com.hendisantika.bookmymovie.service;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/04/20
 * Time: 01.16
 */
public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
