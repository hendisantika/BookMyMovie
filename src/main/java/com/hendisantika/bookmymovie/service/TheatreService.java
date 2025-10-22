package com.hendisantika.bookmymovie.service;

import com.hendisantika.bookmymovie.entity.Theatre;
import com.hendisantika.bookmymovie.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 27/08/25
 * Time: 12.00
 */
@Service
@RequiredArgsConstructor
public class TheatreService {
    private final TheatreRepository theatreRepository;

    public List<Theatre> getAllTheatres() {
        List<Theatre> theatres = new ArrayList<>();
        theatreRepository.findAll().forEach(theatres::add);
        return theatres;
    }
}
