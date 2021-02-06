package org.progmatic.messenger.controllers;

import org.progmatic.messenger.dtos.*;
import org.progmatic.messenger.model.Message;
import org.progmatic.messenger.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class MyRestController {

    MessageService messageService;

    @Autowired
    public MyRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PreAuthorize("hasAuthority('DELETE_MESSAGE')")
    @PutMapping("messages/{messageId}")
    public MessageDeleteResultDTO restoreMessage(
            @PathVariable("messageId") Long messageId,
            @RequestBody MsgArchiveDTO msgArchiveDTO){
        switch (msgArchiveDTO.getAction()){
            case DELETE:
                return messageService.deleteMessage(messageId);
            case RESTORE:
                return messageService.restoreMessage(messageId);
            default:
                throw new RuntimeException("Unknown action: " + msgArchiveDTO.getAction());
        }
    }


    @PostMapping("/messages")
    public MessageResponseDTO createMessage(@RequestBody  MessageRequestDTO req){
        Message m = new Message();
        m.setText(req.getText());
        messageService.addMessage(m);
        MessageResponseDTO ret = new MessageResponseDTO();
        ret.setAuthor(m.getAuthor().getUsername());
        ret.setId(m.getId());
        ret.setText(m.getText());
        return ret;
    }

    @GetMapping("/messages/{msgId}")
    public MessageResponseDTO findMessage(@PathVariable("msgId") Long msgId){
        Message msg = messageService.findMessageById(msgId, 0);
        MessageResponseDTO ret = new MessageResponseDTO();
        ret.setText(msg.getText());
        ret.setId(msg.getId());
        ret.setAuthor(msg.getAuthor().getUsername());
        ret.setCreationTime(msg.getCreationTime());
        return ret;
    }



    @GetMapping("/rest/csrf")
    public CsrfToken getCsrf(CsrfToken token){
        return token;
    }
}
