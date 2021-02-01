package org.progmatic.messenger.services;

import org.progmatic.messenger.model.Authority;
import org.progmatic.messenger.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
public class MyUserDeatailsService  implements UserDetailsService {

    @PersistenceContext
    EntityManager em;

    PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserDeatailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return em.createQuery("select u from User u left join fetch u.authorities where u.username = :name", User.class)
                    .setParameter("name", username)
                    .getSingleResult();
        }
        catch (NoResultException ex){
            throw new UsernameNotFoundException(username);
        }
    }

    public boolean userExists(String username) {
        try {
            User user = em.createQuery("select u from User u where u.username = :name", User.class)
                    .setParameter("name", username)
                    .getSingleResult();
            return true;
        }
        catch (NoResultException ex){
            return false;
        }
    }

    @Transactional
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        em.persist(user);
    }
}
