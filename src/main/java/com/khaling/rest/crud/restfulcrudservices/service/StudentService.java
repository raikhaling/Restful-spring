package com.khaling.rest.crud.restfulcrudservices.service;

import com.khaling.rest.crud.restfulcrudservices.entity.StudentEntity;

import java.util.List;

public interface StudentService {
    List<StudentEntity> getAllStudents();
    StudentEntity getStudentById(int id);
    StudentEntity createStudent(StudentEntity student);
    void deleteStudent(int id);

}
