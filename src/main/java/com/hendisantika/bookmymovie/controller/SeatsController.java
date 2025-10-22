package com.hendisantika.bookmymovie.controller;

import com.hendisantika.bookmymovie.entity.Screening;
import com.hendisantika.bookmymovie.entity.Seat;
import com.hendisantika.bookmymovie.entity.Ticket;
import com.hendisantika.bookmymovie.repository.ScreeningRepository;
import com.hendisantika.bookmymovie.repository.SeatRepository;
import com.hendisantika.bookmymovie.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/04/20
 * Time: 01.23
 */
@Controller
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatsController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final ScreeningRepository screeningRepository;

    @GetMapping
    public String bookSeats(@RequestParam(value = "screeningId", required = true) Long screeningId,
                            @RequestParam(value = "count", required = false) Integer seatCount,
                           @RequestParam(value = "movieName", required = false) String movieName,
                           @RequestParam(value = "moviePoster", required = false) String moviePoster,
                           @RequestParam(value = "theatreName", required = false) String theatreName,
                           @RequestParam(value = "theatreCity", required = false) String theatreCity,
                           @RequestParam(value = "screeningDate", required = false) String screeningDate,
                           @RequestParam(value = "screeningTime", required = false) String screeningTime,
                           Model model) {

        // Fetch the screening
        Optional<Screening> screeningOpt = screeningRepository.findById(screeningId);
        if (screeningOpt.isEmpty()) {
            LOGGER.error("Screening not found for ID: {}", screeningId);
            return "error";
        }

        Screening screening = screeningOpt.get();

        // Fetch all seats for this screen
        List<Seat> allSeats = seatRepository.findByScreenId(screening.getScreenId());

        // Fetch booked tickets for this screening
        List<Ticket> bookedTickets = ticketRepository.findByScreeningId(screeningId);
        Set<Long> bookedSeatIds = bookedTickets.stream()
                .map(ticket -> (long) ticket.getSeatNum())
                .collect(Collectors.toSet());

        // Group seats by row for easy display
        Map<String, List<Seat>> seatsByRow = allSeats.stream()
                .sorted(Comparator.comparing(Seat::getRowId)
                        .thenComparing(Seat::getSeatRowNumber))
                .collect(Collectors.groupingBy(Seat::getRowId, LinkedHashMap::new, Collectors.toList()));

        // Add data to model
        model.addAttribute("screening", screening);
        model.addAttribute("screeningId", screeningId);
        model.addAttribute("seatsByRow", seatsByRow);
        model.addAttribute("bookedSeatIds", bookedSeatIds);
        model.addAttribute("count", seatCount != null ? seatCount : 0);
        model.addAttribute("movieName", movieName);
        model.addAttribute("moviePoster", moviePoster);
        model.addAttribute("theatreName", theatreName);
        model.addAttribute("theatreCity", theatreCity);
        model.addAttribute("screeningDate", screeningDate);
        model.addAttribute("screeningTime", screeningTime);

        LOGGER.info("Loading seats for screening: {}, movie: {}, theatre: {}, date: {}, time: {}. Found {} seats, {} booked",
                screeningId, movieName, theatreName, screeningDate, screeningTime, allSeats.size(), bookedSeatIds.size());

        return "seats";
    }

    @PostMapping("/book")
    @ResponseBody
    public ResponseEntity<?> bookSeats(@RequestBody BookingRequest bookingRequest) {
        try {
            Long screeningId = bookingRequest.getScreeningId();
            List<Long> seatIds = bookingRequest.getSeatIds();

            LOGGER.info("Booking request for screening {} with {} seats: {}", screeningId, seatIds.size(), seatIds);

            // Validate screening exists
            Optional<Screening> screeningOpt = screeningRepository.findById(screeningId);
            if (screeningOpt.isEmpty()) {
                LOGGER.error("Screening not found for ID: {}", screeningId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "Screening not found"));
            }

            Screening screening = screeningOpt.get();

            // Check if any of the requested seats are already booked
            List<Ticket> existingTickets = ticketRepository.findByScreeningId(screeningId);
            Set<Long> bookedSeatIds = existingTickets.stream()
                    .map(ticket -> (long) ticket.getSeatNum())
                    .collect(Collectors.toSet());

            List<Long> alreadyBooked = seatIds.stream()
                    .filter(bookedSeatIds::contains)
                    .collect(Collectors.toList());

            if (!alreadyBooked.isEmpty()) {
                LOGGER.warn("Seats already booked: {}", alreadyBooked);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("success", false, "message", "Some seats are already booked",
                                "bookedSeats", alreadyBooked));
            }

            // Create tickets for each selected seat
            List<Ticket> newTickets = new ArrayList<>();
            for (Long seatId : seatIds) {
                Ticket ticket = new Ticket();
                ticket.setScreeningId(screeningId);
                ticket.setSeatNum(seatId.intValue());
                newTickets.add(ticket);
            }

            // Save all tickets
            ticketRepository.saveAll(newTickets);

            // Update screening booked tickets count
            screening.setBookedTickets(screening.getBookedTickets() + seatIds.size());
            screeningRepository.save(screening);

            LOGGER.info("Successfully booked {} seats for screening {}", seatIds.size(), screeningId);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Seats booked successfully",
                    "ticketCount", seatIds.size(),
                    "screeningId", screeningId
            ));

        } catch (Exception e) {
            LOGGER.error("Error booking seats", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error booking seats: " + e.getMessage()));
        }
    }

    // DTO for booking request
    public static class BookingRequest {
        private Long screeningId;
        private List<Long> seatIds;

        public Long getScreeningId() {
            return screeningId;
        }

        public void setScreeningId(Long screeningId) {
            this.screeningId = screeningId;
        }

        public List<Long> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Long> seatIds) {
            this.seatIds = seatIds;
        }
    }
}
