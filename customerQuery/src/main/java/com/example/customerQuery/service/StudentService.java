package com.example.customerQuery.service;

import com.example.customerQuery.dao.StudentRepo;
import com.example.customerQuery.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepo repository;

    public int saveStudent(Student student) {
        return repository.save(student).getStudentId();
    }

    public Student studentByFirstName(String firstName) {
        return repository.findByFirstName(firstName);
    }

    public Student studentByFirstNameQuery(String firstName) {
        return repository.findByFirstNameQuery(firstName);
    }

    public Student studentByStudentId(Integer studentId) {
        return repository.findByStudentId(studentId);
    }
}
