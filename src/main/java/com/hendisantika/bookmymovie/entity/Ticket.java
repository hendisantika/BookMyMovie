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
 * Time: 04.13
 */

@Entity
@Table(name = "TICKET")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @Column(name = "TICKET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketId;

    @Column(name = "SCREENING_ID")
    private long screeningId;

    @Column(name = "SEAT_NUM")
    private int seatNum;
}
