package com.orchid.server.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserJPAService userJPAService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userJPAService.findByUserName(username);
    }

}
