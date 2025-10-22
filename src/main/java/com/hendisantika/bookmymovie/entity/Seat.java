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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seatId;

    @Column(name = "ROW_ID", length = 1, columnDefinition = "CHAR(1)")
    private String rowId;

    @Column(name = "SEAT_ROW_NUMBER")
    private int seatRowNumber;

    @Column(name = "SCREEN_ID")
    private long screenId;
}
