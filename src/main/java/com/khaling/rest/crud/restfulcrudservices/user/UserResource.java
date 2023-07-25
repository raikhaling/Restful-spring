package com.khaling.rest.crud.restfulcrudservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserResource {

    @Autowired  //creates a instance of UserDaoService and autowires it
    private UserDaoService service;

    //retrieve all users
    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }
    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable int id){
        return service.findOne(id);
    }
    @PostMapping(path = "/save-user")
    public User saveUser(User user){
        return service.save(user);
    }
}
