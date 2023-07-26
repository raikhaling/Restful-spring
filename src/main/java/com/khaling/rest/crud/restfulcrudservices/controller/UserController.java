package com.khaling.rest.crud.restfulcrudservices.controller;

import com.khaling.rest.crud.restfulcrudservices.exception.ValidationException;
import com.khaling.rest.crud.restfulcrudservices.service.UserService;
import com.khaling.rest.crud.restfulcrudservices.exception.UserNotFoundException;
import com.khaling.rest.crud.restfulcrudservices.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired  //creates a instance of UserDaoService and autowires it
    private UserService service;

    //retrieve all users
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        List<User> users =  service.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException("No any users found");
        }
        return users;
    }
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id){
        User user = service.findOne(id);
        if(user == null){
            throw new UserNotFoundException("id -> "+id);
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        throw new ValidationException(message);
        }
        if (user == null){
            throw new UserNotFoundException("Not a proper format to be added.");
        }
        User savedUser = service.save(user);
        //Created users/{id}
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable int id){
        User deletedUser = service.deleteById(id);
        if(deletedUser == null)
            throw new UserNotFoundException("id -> "+id);
        return deletedUser;
    }
}
