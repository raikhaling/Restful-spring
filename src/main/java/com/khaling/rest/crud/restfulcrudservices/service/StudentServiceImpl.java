package com.khaling.rest.crud.restfulcrudservices.service;

import com.khaling.rest.crud.restfulcrudservices.entity.StudentEntity;
import com.khaling.rest.crud.restfulcrudservices.exception.UserNotFoundException;
import com.khaling.rest.crud.restfulcrudservices.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    @Override
    public List<StudentEntity> getAllStudents() {
       List<StudentEntity> students = studentRepository.findAll();
       if(students.isEmpty()){
           throw new UserNotFoundException("No any users found");
       }
       return students;
    }

    @Override
    public StudentEntity getStudentById(int id) {
        Optional<StudentEntity> student = studentRepository.findById(id);
        if(!student.isPresent()){
            throw new UserNotFoundException("id - "+id);
        }
        return student.get();
    }

    @Override
    public StudentEntity createStudent(StudentEntity student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }
}
