package com.hendisantika.bookmymovie.controller;

import com.hendisantika.bookmymovie.entity.User;
import com.hendisantika.bookmymovie.service.SecurityService;
import com.hendisantika.bookmymovie.service.UserService;
import com.hendisantika.bookmymovie.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by IntelliJ IDEA.
 * Project : book-mymovie
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/04/20
 * Time: 01.24
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }
}
