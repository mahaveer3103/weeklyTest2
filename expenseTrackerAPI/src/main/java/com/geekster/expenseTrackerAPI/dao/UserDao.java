package com.geekster.expenseTrackerAPI.dao;

import com.geekster.expenseTrackerAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {

    @Query(value = "Select * from tbl_user where username = :username and status_id = 1", nativeQuery = true)
    List<User> findByUserName(String username);

    @Query(value = "select * from tbl_user where status_id= 1",nativeQuery = true)
    List<User> findUserById(int userId);
}
