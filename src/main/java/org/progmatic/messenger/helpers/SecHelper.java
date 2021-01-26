package org.progmatic.messenger.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecHelper.class);

    public static UserDetails getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetails) {
                return (UserDetails) principal;
            }
        }
        return null;
    }

    public static boolean hasAuthority(String authority) {
        UserDetails loggedInUser = getLoggedInUser();
        if(loggedInUser != null){
            return  hasAuthority(loggedInUser, authority);
        }
        return false;
    }

    public static boolean hasAuthority(UserDetails u, String authority) {

        Collection<? extends GrantedAuthority> authorities
                = u.getAuthorities();
        for (GrantedAuthority auth : authorities) {
            if (auth.getAuthority().equals(authority)) {
                return true;
            }
        }
        return false;
    }


}
