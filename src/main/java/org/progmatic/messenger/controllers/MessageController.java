package org.progmatic.messenger.controllers;

import org.progmatic.messenger.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    List<Message> messages = new ArrayList<>();

    public MessageController() {
        messages.add(new Message("Aladár", "helló", LocalDateTime.now()));
        messages.add(new Message("Kriszta", "csövike", LocalDateTime.now()));
        messages.add(new Message("MZ/X", "hl", LocalDateTime.now()));
    }

    @GetMapping("/messages")
    public String showMessages(Model model){
        model.addAttribute("allmessages", messages);
        return "messageslist";
    }



}
