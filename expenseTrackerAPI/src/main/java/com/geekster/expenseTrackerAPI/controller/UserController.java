package com.geekster.expenseTrackerAPI.controller;

import com.geekster.expenseTrackerAPI.dao.StatusDao;
import com.geekster.expenseTrackerAPI.dao.UserDao;
import com.geekster.expenseTrackerAPI.dto.UserLoginDto;
import com.geekster.expenseTrackerAPI.dto.UserRegisterDto;
import com.geekster.expenseTrackerAPI.model.Status;
import com.geekster.expenseTrackerAPI.model.User;
import com.geekster.expenseTrackerAPI.service.UserService;
import com.geekster.expenseTrackerAPI.utils.CommonUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    StatusDao statusDao;

    @Autowired
    UserDao userDao;

    @PostMapping(value = "/register-user")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDto requestUser){
        JSONObject requestObject = new JSONObject(requestUser);
        JSONObject errorList = validateUser(requestObject);
        if(errorList.isEmpty()){
            User user = setUser(requestObject);
            int userId = service.registerUser(user);
            return new ResponseEntity<>("User registered with userId - "+userId, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(errorList.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/login-user")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto requestLogin){
        JSONObject jsonLogin = new JSONObject(requestLogin);
        JSONObject errorList = validateLogin(jsonLogin);
        if(errorList.isEmpty()){
            return service.loginUser(jsonLogin);
        }
        return new ResponseEntity<>(errorList.toString(),HttpStatus.BAD_REQUEST);
    }

    private JSONObject validateLogin(JSONObject jsonLogin) {
        JSONObject errorList = new JSONObject();
        if(!jsonLogin.has("username")){
            errorList.put("Missing parameter","username");
        }
        if(!jsonLogin.has("password")){
            errorList.put("Missing parameter","password");
        }
        return errorList;
    }

    private User setUser(JSONObject requestObject) {
        User user = new User();
        user.setUsername(requestObject.getString("username"));
        user.setPassword(requestObject.getString("password"));
        user.setFirstName(requestObject.getString("first_name"));
        user.setPhoneNumber(requestObject.getString("phone_number"));
        user.setEmailId(requestObject.getString("email_id"));
        if(requestObject.has("last_name")){
            user.setLastName(requestObject.getString("last_name"));
        }
        if(requestObject.has("age")){
            user.setAge(requestObject.getInt("age"));
        }
        Status status = statusDao.findById(1).get();
        user.setStatus(status);
        return user;
    }

    private JSONObject validateUser(JSONObject requestObject) {
        JSONObject errorList = new JSONObject();

        if(requestObject.has("username")){
            String username = requestObject.getString("username");
            List<User> userList = userDao.findByUserName(username);
            if(userList.size()>0){
                errorList.put(username,"This username already exist please add another username");
            }
        }else {
            errorList.put("username","Missing parameter");
        }
        if(requestObject.has("password")){
            String password = requestObject.getString("password");
            if(!CommonUtils.isValidPassword(password)){
                errorList.put("password", "Please enter valid password eg: User@123");
            }
        }else {
            errorList.put("password","Missing parameter");
        }
        if(!requestObject.has("first_name")){
            errorList.put("first_name","Missing parameter");
        }
        if(requestObject.has("phone_number")){
            String phoneNumber = requestObject.getString("phone_number");
            if(!CommonUtils.isValidPhoneNumber(phoneNumber)){
                errorList.put(phoneNumber,"Please enter valid phone number");
            }
        }else {
            errorList.put("phone_number","Missing parameter");
        }
        if(requestObject.has("email_id")){
            String email = requestObject.getString("email_id");
            if(!CommonUtils.isValidEmail(email)){
                errorList.put("emailId","Please enter valid email");
            }
        }else {
            errorList.put("email_id","Missing parameter");
        }
        return errorList;
    }
}
