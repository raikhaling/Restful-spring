package com.khaling.rest.crud.restfulcrudservices.controller;

import com.khaling.rest.crud.restfulcrudservices.entity.StudentEntity;
import com.khaling.rest.crud.restfulcrudservices.exception.ValidationException;
import com.khaling.rest.crud.restfulcrudservices.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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
    @GetMapping("/students")
    public List<StudentEntity> retrieveAllUsers() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public EntityModel<StudentEntity> getUserById(@PathVariable int id) {
        StudentEntity student = studentService.getStudentById(id);
        //HATEOAS
        // Wrap the student object with HATEOAS links
        EntityModel<StudentEntity> entityModel = EntityModel.of(student);
        // Add the self-link for the current user
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(this.getClass()).getUserById(id)).withSelfRel());
        // Add the link to 'retrieveAllUsers()' with the relation 'all-users'
        entityModel.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/students")
    public ResponseEntity<Object> createUser(@Valid @RequestBody StudentEntity student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            throw new ValidationException(message);
        }
        StudentEntity savedStudent = studentService.createStudent(student);
        //Created users/{id}
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").buildAndExpand(savedStudent.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/students/{id}")
    public void deleteUser(@PathVariable int id) {
        studentService.deleteStudent(id);

    }
}
