package org.progmatic.messenger.controllers;

import org.progmatic.messenger.dtos.MessageDeleteResultDTO;
import org.progmatic.messenger.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class AjaxMessageController {

    MessageService messageService;

    @Autowired
    public AjaxMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PreAuthorize("hasAuthority('DELETE_MESSAGE')")
    @PostMapping("message/restore/{messageId}")
    public MessageDeleteResultDTO restoreMessage(@PathVariable("messageId") Long messageId){
        return messageService.restoreMessage(messageId);
    }

    @PreAuthorize("hasAuthority('DELETE_MESSAGE')")
    @PostMapping("message/delete/{messageId}")
    public MessageDeleteResultDTO deleteMessage(@PathVariable("messageId") Long messageId){
        return messageService.deleteMessage(messageId);
    }
}
