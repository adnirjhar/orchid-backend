package com.orchid.server.account;

import com.orchid.server.config.application_config.ApplicationConfigService;
import com.orchid.server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserControllerImpl implements UserController {

    @Autowired
    private UserJPAService userJPAService;

    @Autowired
    private ApplicationConfigService configService;


    @Override
    public User findByUserName(String userName) {
        return userJPAService.findByUserName(userName);
    }

    @Override
    public User register(User newUser) throws Exception {
        User foundUser = this.userJPAService.findByUserName(newUser.getUsername());
        if (foundUser != null) {
            throw new Exception("Username already exists.");
        }
        else {
            newUser.setPassword(this.configService.passwordEncoder().encode(newUser.getPassword()));
            return this.userJPAService.save(newUser);
        }
    }

    @Override
    public Object authenticate(User user) throws Exception {

        UserDetails userDetails = this.userJPAService.findByUserName(user.getUsername());
        if (userDetails == null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new Exception("User not found");
        }
        boolean passIsMatch = this.configService.passwordEncoder().matches(user.getPassword(),userDetails.getPassword());
        if (passIsMatch) {

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication.getPrincipal();
        }

        SecurityContextHolder.getContext().setAuthentication(null);
        throw new Exception("Password does not match");
    }

    @Override
    public Object logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        Map<String,String> map = new HashMap<>();
        map.put("message","User has logged out");
        return map;
    }
}
