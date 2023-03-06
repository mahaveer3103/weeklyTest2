package com.example.customerQuery.dao;

import com.example.customerQuery.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepo extends JpaRepository<Student,Integer> {
    Student findByFirstName(String firstName);

    @Query(value = "select * from tbl_student where first_name=:firstName and active= true",nativeQuery = true)
    Student findByFirstNameQuery(String firstName);

    Student findByStudentId(Integer studentId);

}
