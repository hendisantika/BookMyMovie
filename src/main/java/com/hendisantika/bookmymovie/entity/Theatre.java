package com.hendisantika.bookmymovie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/04/20
 * Time: 04.11
 */
@Entity
@Table(name = "THEATRE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Theatre {
    @Id
    @Column(name = "THEATRE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long theatreId;

    @Column(name = "THEATRE_NAME")
    private String theatreName;

    @Column(name = "THEATRE_CITY")
    private String theatreCity;
}
