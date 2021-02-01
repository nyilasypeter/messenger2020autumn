/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.progmatic.messenger.configurators;


import org.progmatic.messenger.autodaos.AuthorityAutoDao;
import org.progmatic.messenger.autodaos.UserAutoDao;
import org.progmatic.messenger.model.Authority;
import org.progmatic.messenger.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

/**
 *
 * @author peti
 */
@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);



    @PersistenceContext
    EntityManager em;

    protected UserAutoDao userAutoDao;

    protected PasswordEncoder passwordEncoder;

    protected AuthorityAutoDao authorityAutoDao;

    @Autowired
    public DataLoader(UserAutoDao userAutoDao, PasswordEncoder passwordEncoder, AuthorityAutoDao authorityAutoDao) {
        this.userAutoDao = userAutoDao;
        this.passwordEncoder = passwordEncoder;
        this.authorityAutoDao = authorityAutoDao;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        createAuthorities();
        createUsers();
    }

    private void createAuthorities() {
        long count = authorityAutoDao.count();
        if(count == 0){
            Authority at = new Authority();
            at.setName(Authority.AUTH_DELETE_MESSAGE);
            authorityAutoDao.save(at);
        }
    }


    protected void createUsers() {
        long nrOfUsers = userAutoDao.count();
        
        if (nrOfUsers == 0) {
            Authority deleteMessageAuth = authorityAutoDao.findByName(Authority.AUTH_DELETE_MESSAGE);
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@admin.hu");
            admin.setDateOfBirth(LocalDate.of(1970, 1, 1));
            admin.getAuthorities().add(deleteMessageAuth);
            userAutoDao.save(admin);
            LOG.debug("Admin user created.");


            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setEmail("user@user.hu");
            user.setDateOfBirth(LocalDate.of(1970, 1, 1));
            userAutoDao.save(user);
            LOG.debug("User user created.");
        }
    }
}
