package com.hendisantika.bookmymovie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long theatreId;

    @Column(name = "THEATRE_NAME")
    private String theatreName;

    @Column(name = "THEATRE_CITY")
    private String theatreCity;
}
