package com.hendisantika.bookmymovie.business.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/04/20
 * Time: 04.03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieScreening {
    private String movieName;
    private String moviePosterURL;
    private long theatreId;
    private String theatreName;
    private String theatreCity;
    private String screeningDate;
    private String screeningTime;
    private int numSeats;
}
