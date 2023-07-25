package com.khaling.rest.crud.restfulcrudservices.controller;

import com.khaling.rest.crud.restfulcrudservices.Dao.UserDaoService;
import com.khaling.rest.crud.restfulcrudservices.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/users")
    public User createUser(@RequestBody User user){
        return service.save(user);
    }
}
