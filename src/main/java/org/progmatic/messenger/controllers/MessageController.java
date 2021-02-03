package org.progmatic.messenger.controllers;

import org.progmatic.messenger.model.Message;
import org.progmatic.messenger.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
public class MessageController {

    MessageService messageService;

    ApplicationContext context;

    @Autowired
    public MessageController(MessageService messageService, ApplicationContext context) {
        this.messageService = messageService;
        this.context = context;
    }



    @GetMapping("/messages")
    public String showMessages(Model model){
        MessageService ms = context.getBean(MessageService.class);
        model.addAttribute("allmessages", ms.getAllMessages());
        return "messageslist";
    }

    @GetMapping("/message/{id}")
    public String showOneMessages(
            @PathVariable("id") Long messageId,
            @RequestParam("sleep") Integer sleep,
            Model model
    ){
        Message m = messageService.findMessageById(messageId, sleep);
        if(m != null) {
            model.addAttribute("message", m);
        }
        return "oneMessage";
    }

    @GetMapping("createmessage")
    public String showCreateMessage(@ModelAttribute("msg") Message message, Model model){
        return "createMessage";
    }

    @PostMapping("createmessage")
    public String createMessage(@ModelAttribute("msg") @Valid Message message, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "createMessage";
        }
        message.setCreationTime(LocalDateTime.now());
        messageService.addMessage(message);
        return "redirect:/messages";
    }



    @GetMapping("/appendTextToMsg")
    public String appendTextToMsg(
            @RequestParam("id") Long id,
            @RequestParam("text") String text,
            @RequestParam("sleep") Integer sleep
    ){
        messageService.appendTextToMessage(id, text, sleep);
        return "redirect:/messages";
    }



}
