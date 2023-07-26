package com.khaling.rest.crud.restfulcrudservices.controller;

import com.khaling.rest.crud.restfulcrudservices.entity.StudentEntity;
import com.khaling.rest.crud.restfulcrudservices.exception.ValidationException;
import com.khaling.rest.crud.restfulcrudservices.repository.StudentRepository;
import com.khaling.rest.crud.restfulcrudservices.exception.UserNotFoundException;
import com.khaling.rest.crud.restfulcrudservices.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public StudentEntity getUserById(@PathVariable int id){
        Optional<StudentEntity> user = studentRepository.findById(id);
        if(!user.isPresent()){ // confirms user is coming back as a proper object
            throw new UserNotFoundException("id -> "+id);
        }
        return user.get();
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
