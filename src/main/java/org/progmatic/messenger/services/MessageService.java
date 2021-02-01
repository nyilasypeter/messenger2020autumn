package org.progmatic.messenger.services;

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

    public Message findMessageById(Long messageId){
        Optional<Message> first;
        if(!SecHelper.hasAuthority("DELETE_MESSAGE")){
            return em.find(Message.class, messageId);
        }
        else{
            try {
                return em.createQuery("select m from Message m where m.isDeleted = false and m.id = :id", Message.class)
                        .setParameter("id", messageId)
                        .getSingleResult();
            }
            catch (NoResultException ex){
                return null;
            }
        }
    }

    @Transactional
    public void addMessage(Message msg){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        msg.setAuthor(user);
        em.persist(msg);
    }

    @Transactional
    public void deleteMessage(int messageId){
        Message msg = em.find(Message.class, messageId);
        if(msg != null){
            msg.setDeleted(true);
        }
    }

    @Transactional
    public void restoreMessage(int messageId){
        Message msg = em.find(Message.class, messageId);
        if(msg != null){
            msg.setDeleted(false);
        }
    }
}
