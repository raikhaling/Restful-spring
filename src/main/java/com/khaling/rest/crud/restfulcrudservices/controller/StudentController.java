package com.khaling.rest.crud.restfulcrudservices.controller;

import com.khaling.rest.crud.restfulcrudservices.entity.StudentEntity;
import com.khaling.rest.crud.restfulcrudservices.exception.ValidationException;
import com.khaling.rest.crud.restfulcrudservices.repository.StudentRepository;
import com.khaling.rest.crud.restfulcrudservices.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

 //   @Autowired  //creates a instance of UserDaoService and autowires it
    private StudentRepository studentRepository;
    public StudentController(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }


    @GetMapping("/")
    public String returnThym(Model model) {
        model.addAttribute("message", "Hello from Thymeleaf!");
        return "hello";
    }
    @GetMapping("/style")
    public String style() {
        return "add-css-js-demo";
    }
    //retrieve all users
    @GetMapping("/users")
    public List<StudentEntity> retrieveAllUsers(){
        List<StudentEntity> users =  studentRepository.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException("No any users found");
        }
        return users;
    }
    @GetMapping("/users/{id}")
    public EntityModel<StudentEntity> getUserById(@PathVariable int id){
        Optional<StudentEntity> user = studentRepository.findById(id);
        if(!user.isPresent()){ // confirms user is coming back as a proper object
            throw new UserNotFoundException("id -> "+id);
        }
        //HATEOAS
        // Wrap the user object with HATEOAS links
        EntityModel<StudentEntity> entityModel = EntityModel.of(user.get());

        // Add the self-link for the current user
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(this.getClass()).getUserById(id)).withSelfRel());

        // Add the link to 'retrieveAllUsers()' with the relation 'all-users'
        entityModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody StudentEntity student, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        throw new ValidationException(message);
        }
//        if (student == null){
//            throw new UserNotFoundException("Not a proper format to be added.");
//        }
        StudentEntity savedStudent = studentRepository.save(student);
        //Created users/{id}
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").buildAndExpand(savedStudent.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        studentRepository.deleteById(id);

    }
}
