package com.hendisantika.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/04/20
 * Time: 04.06
 */
@Entity
@Table(name = "MOVIE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @Column(name = "MOVIE_NAME")
    private String movieName;

    @Column(name = "MOVIE_ID")
    private long movieId;

    @Column(name = "MOVIE_POSTER_URL")
    private String moviePosterUrl;

    @Column(name = "MOVIE_TAGS")
    private String movieTags;

}
