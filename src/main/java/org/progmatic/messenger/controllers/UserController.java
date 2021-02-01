package org.progmatic.messenger.controllers;


import org.progmatic.messenger.model.Authority;
import org.progmatic.messenger.model.User;
import org.progmatic.messenger.services.MyUserDeatailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private MyUserDeatailsService userService;

    @Autowired
    public UserController(MyUserDeatailsService userService) {
        this.userService = userService;
    }



    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.userExists(user.getUsername())) {
            bindingResult.rejectValue("username", "username.exists", "Username is already taken!");
            return "register";
        }
        userService.createUser(user);
        LOGGER.debug("register method: user created");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
