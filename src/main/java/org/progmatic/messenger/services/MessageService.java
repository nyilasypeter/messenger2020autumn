package org.progmatic.messenger.services;

import org.progmatic.messenger.model.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    List<Message> messages = new ArrayList<>();

    public MessageService() {
        messages.add(new Message("Aladár", "helló", LocalDateTime.now()));
        messages.add(new Message("Kriszta", "csövike", LocalDateTime.now()));
        messages.add(new Message("MZ/X", "hl", LocalDateTime.now()));
    }

    public List<Message> getAllMessages(){
        return messages;
    }

    public Message findMessageById(int messageId){
        Optional<Message> first = messages.stream().filter(m -> m.getId() == messageId).findFirst();
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
}
