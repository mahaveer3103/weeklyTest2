package com.example.customerQuery.controller;

import com.example.customerQuery.model.Student;
import com.example.customerQuery.service.StudentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

    @Autowired
    StudentService service;

    @PostMapping("/student")
    public ResponseEntity<String> saveStudent(@RequestBody String requestStudent){
        Student student = setStudent(requestStudent);
        int id = service.saveStudent(student);
        return new ResponseEntity<>("Saved successfully with id - "+id, HttpStatus.CREATED);
    }

    @GetMapping("/studentByFirstname")
    public ResponseEntity<Student> studentByFirstName(@RequestParam String firstName){
        Student student = service.studentByFirstName(firstName);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    @GetMapping("/studentByFirstnameQuery")
    public ResponseEntity<Student> studentByFirstNameQuery(@RequestParam String firstName){
        Student student = service.studentByFirstNameQuery(firstName);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    @GetMapping("/studentId")
    public ResponseEntity<Student> studentByStudentId(@RequestParam Integer studentId){
        Student student = service.studentByStudentId(studentId);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    private Student setStudent(String requestStudent) {
        JSONObject json = new JSONObject(requestStudent);
        Student student = new Student();
        student.setActive(json.getBoolean("active"));
        student.setAge(json.getInt("age"));
        student.setFirstName(json.getString("firstName"));
        student.setLastName(json.getString("lastName"));
        Date currDate = new Date(System.currentTimeMillis());
        student.setAdmission(currDate);
        return student;
    }
}
