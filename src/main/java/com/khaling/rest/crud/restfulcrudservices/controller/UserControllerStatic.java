package com.khaling.rest.crud.restfulcrudservices.controller;

import com.khaling.rest.crud.restfulcrudservices.entity.User;
import com.khaling.rest.crud.restfulcrudservices.exception.UserNotFoundException;
import com.khaling.rest.crud.restfulcrudservices.exception.ValidationException;
import com.khaling.rest.crud.restfulcrudservices.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserControllerStatic {

 //   @Autowired  //creates a instance of UserDaoService and autowires it
    private UserService userService;
    public UserControllerStatic(UserService userService){
        this.userService = userService;
    }


    //retrieve all users
    @GetMapping("st/users")
    public List<User> retrieveAllUsers(){
        List<User> users =  userService.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException("No any users found");
        }
        return users;
    }
    @GetMapping("st/users/{id}")
    public User getUserById(@PathVariable int id){
        User user = userService.findOne(id);
        if(user == null){
            throw new UserNotFoundException("id -> "+id);
        }
        return user;
    }

    @PostMapping("st/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        throw new ValidationException(message);
        }
        if (user == null){
            throw new UserNotFoundException("Not a proper format to be added.");
        }
        User savedUser = userService.save(user);
        //Created users/{id}
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("st/users/{id}")
    public User deleteUser(@PathVariable int id){
        User deletedUser = userService.deleteById(id);
        if(deletedUser == null)
            throw new UserNotFoundException("id -> "+id);
        return deletedUser;
    }
}
