package org.progmatic.messenger.services;

import org.progmatic.messenger.helpers.SecHelper;
import org.progmatic.messenger.model.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    List<Message> messages = Collections.synchronizedList(new ArrayList<>());

    public MessageService() {
        messages.add(new Message("Aladár", "helló", LocalDateTime.now()));
        messages.add(new Message("Kriszta", "csövike", LocalDateTime.now()));
        messages.add(new Message("MZ/X", "hl", LocalDateTime.now()));
    }

    public List<Message> getAllMessages(){
        if(SecHelper.hasAuthority("DELETE_MESSAGE")) {
            return messages;
        }
        else{
            return messages.stream().filter(m -> !m.getDeleted()).collect(Collectors.toList());
        }
    }

    public Message findMessageById(int messageId){
        Optional<Message> first;
        if(!SecHelper.hasAuthority("DELETE_MESSAGE")){
            first = messages.stream().filter(m -> !m.getDeleted() && m.getId() == messageId).findFirst();
        }
        else{
            first = messages.stream().filter(m -> m.getId() == messageId).findFirst();
        }
        if(first.isPresent()) {
            return first.get();
        }
        return null;
    }

    public void addMessage(Message msg){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        msg.setAuthor(user.getUsername());
        messages.add(msg);
    }

    public void deleteMessage(int messageId){
        Message msg = findMessageById(messageId);
        if(msg != null){
            msg.setDeleted(true);
        }
    }

    public void restoreMessage(int messageId){
        Message msg = findMessageById(messageId);
        if(msg != null){
            msg.setDeleted(false);
        }
    }
}
