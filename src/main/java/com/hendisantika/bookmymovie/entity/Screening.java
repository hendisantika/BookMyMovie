package com.hendisantika.bookmymovie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/04/20
 * Time: 04.08
 */
@Entity
@Table(name = "SCREENING")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Screening implements Cloneable {
    @Id
    @Column(name = "SCREENING_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long screeningId;

    @Column(name = "THEATRE_ID")
    private long theatreId;

    @Column(name = "SCREEN_ID")
    private long screenId;

    @Column(name = "MOVIE_NAME")
    private String movieName;

    @Column(name = "SCREENING_DATE")
    @Temporal(TemporalType.DATE)
    private Date screeningDate;

    @Column(name = "SCREENING_TIME")
    private java.sql.Time screeningTime;

    @Column(name = "BOOKED_TICKETS")
    private int bookedTickets;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
