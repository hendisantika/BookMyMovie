package com.hendisantika;

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
 * Time: 04.10
 */
@Entity
@Table(name = "SEAT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @Column(name = "SEAT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long seat_id;

    @Column(name = "ROW_ID")
    private char row_id;

    @Column(name = "ROW_NUMBER")
    private int row_number;

    @Column(name = "SCREEN_ID")
    private long screen_id;
}
