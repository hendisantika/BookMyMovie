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
 * Time: 04.07
 */
@Entity
@Table(name = "SCREEN")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Screen {
    @Id
    @Column(name = "SCREEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long screenId;

    @Column(name = "THEATRE_ID")
    private long theatreId;

    @Column(name = "SEATS_NUM")
    private int seatsNum;
}
