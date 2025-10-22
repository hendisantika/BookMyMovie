package com.hendisantika.bookmymovie.controller;

import com.hendisantika.bookmymovie.entity.Theatre;
import com.hendisantika.bookmymovie.service.TheatreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Slf4j
@Controller
@RequestMapping("/theatres")
@RequiredArgsConstructor
public class TheatreController {
    private final TheatreService theatreService;

    @GetMapping
    public String getTheatres(Model model) {
        List<Theatre> theatres = theatreService.getAllTheatres();
        model.addAttribute("theatres", theatres);
        log.info("Retrieved {} theatres", theatres.size());
        return "theatres";
    }
}
