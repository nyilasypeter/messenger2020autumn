package org.progmatic.messenger.services;

import org.progmatic.messenger.dtos.MessageDeleteResultDTO;
import org.progmatic.messenger.helpers.SecHelper;
import org.progmatic.messenger.model.Message;
import org.progmatic.messenger.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @PersistenceContext
    EntityManager em;


    public List<Message> getAllMessages(){
        if(SecHelper.hasAuthority("DELETE_MESSAGE")) {
            return em.createQuery("select m from Message m ", Message.class).getResultList();
        }
        else{
            return em.createQuery("select m from Message m where m.isDeleted = false", Message.class).getResultList();
        }
    }

    @Transactional
    public Message findMessageById(Long messageId, Integer sleep){
        Message msg;
        if(!SecHelper.hasAuthority("DELETE_MESSAGE")){
            msg =  em.find(Message.class, messageId);
        }
        else{
            try {
                msg = em.createQuery("select m from Message m where m.isDeleted = false and m.id = :id", Message.class)
                        .setParameter("id", messageId)
                        .getSingleResult();
            }
            catch (NoResultException ex){
                return null;
            }
        }
        try {
            Thread.sleep(1000*sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg;

    }

    @Transactional
    public void addMessage(Message msg){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        msg.setAuthor(user);
        em.persist(msg);
    }

    @Transactional
    public MessageDeleteResultDTO deleteMessage(Long messageId){
        MessageDeleteResultDTO res =  new MessageDeleteResultDTO();
        res.setMsgId(messageId);
        Message msg = em.find(Message.class, messageId);
        if(msg != null){
            msg.setDeleted(true);
            res.setSuccessFullyDeleted(true);
        }
        return res;
    }

    @Transactional
    public MessageDeleteResultDTO restoreMessage(Long messageId){
        MessageDeleteResultDTO res =  new MessageDeleteResultDTO();
        res.setMsgId(messageId);
        Message msg = em.find(Message.class, messageId);
        if(msg != null){
            msg.setDeleted(false);
            res.setSuccessFullyRestored(true);
        }
        return res;
    }

    @Transactional
    public void appendTextToMessage(Long messageId, String newText, Integer sleep){
        Message msg = em.find(Message.class, messageId);
        try {
            Thread.sleep(1000*sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        msg.setText(newText);
    }
}
