package com.orchid.server.account;

import com.orchid.server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserJPAService {

    @Autowired
    UserJPARepository userJPARepository;

    public User save(User user) {
        return userJPARepository.saveAndFlush(user);
    }

    public User findByUserName(String userName) {
        return userJPARepository.findOneByEmail(userName);
    }
}
