package com.orchid.server.account;

import com.orchid.server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserControllerREST {

    @Autowired
    UserController userController;

    @RequestMapping(method = RequestMethod.POST, value = "/admin/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {

        try {
            User savedNewUser = userController.register(newUser);
            return new ResponseEntity<User>(savedNewUser,HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/account/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            return new ResponseEntity<Object>(userController.authenticate(user),HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/account/logout")
    public Object logout() {
        try {
            return new ResponseEntity<Object>(userController.logout(),HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<Object>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/account/getCurrentUser")
    public ResponseEntity<?> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean authenticated = !auth.getPrincipal().equals("anonymousUser");
        if (!authenticated) {
            return new ResponseEntity<Boolean>(false,HttpStatus.OK);
        }
        return new ResponseEntity<Object>(auth.getPrincipal(),HttpStatus.OK);
    }


}
