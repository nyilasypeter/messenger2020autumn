package org.progmatic.messenger.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    Random r = new Random();

    @RequestMapping(value = {"/", "/home"}, method = GET)
    public String startPage(Model model){
        LOGGER.debug("startPage method started");
        LOGGER.info("startPage info");
        model.addAttribute("message", "hello");
        return "greeting";
    }

    @RequestMapping(value = {"/dobokocka"}, method = GET)
    public String throwDice(
            @RequestParam(name = "sides", required = false, defaultValue = "6") int sides,
            Model model){
        model.addAttribute("sides", sides);
        model.addAttribute("number", r.nextInt(sides)+1);
        return "dice";
    }


}
