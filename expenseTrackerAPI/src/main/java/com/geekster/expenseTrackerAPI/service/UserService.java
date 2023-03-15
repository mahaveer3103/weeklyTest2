package com.geekster.expenseTrackerAPI.service;

import com.geekster.expenseTrackerAPI.dao.UserDao;
import com.geekster.expenseTrackerAPI.model.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public int registerUser(User user) {
        return userDao.save(user).getUserId();
    }

    public ResponseEntity<String> loginUser(JSONObject jsonLogin) {
        String username = jsonLogin.getString("username");
        String password = jsonLogin.getString("password");
        List<User> usersList = userDao.findByUserName(username);
        if(!usersList.isEmpty()){
            User user = usersList.get(0);
            if(password.equals(user.getPassword())){
                return new ResponseEntity<>("Logged-in Successfully",HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid password",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
    }
}
