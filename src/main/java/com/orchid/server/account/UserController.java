package com.orchid.server.account;


import com.orchid.server.domain.User;

public interface UserController {

    public User findByUserName(String userName);

    public User register(User newUser) throws Exception;

    public Object authenticate(User newUser) throws Exception;

    public Object logout();
}
