package com.geekster.expenseTrackerAPI.controller;

import com.geekster.expenseTrackerAPI.dao.StatusDao;
import com.geekster.expenseTrackerAPI.dao.UserDao;

import com.geekster.expenseTrackerAPI.dto.ExpenseDto;
import com.geekster.expenseTrackerAPI.model.Expense;
import com.geekster.expenseTrackerAPI.model.Status;
import com.geekster.expenseTrackerAPI.model.User;
import com.geekster.expenseTrackerAPI.service.ExpenseService;
import com.geekster.expenseTrackerAPI.utils.CommonUtils;
import jakarta.annotation.Nullable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    @Autowired
    UserDao userDao;

    @Autowired
    StatusDao statusDao;

    @Autowired
    ExpenseService service;

    @PostMapping("/add-expense")
    public ResponseEntity<String> addExpense(@RequestBody ExpenseDto requestExpense){
        JSONObject requestJson = new JSONObject(requestExpense);
        JSONObject errorList = validateExpense(requestJson);
        if(errorList.isEmpty()){
            Expense expense = setExpense(requestJson);
            int expenseId = service.addExpense(expense);
            return new ResponseEntity<>("Expense saved with id - "+expenseId,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(errorList.toString(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-expense")
    public ResponseEntity<String> getExpenses(@Nullable @RequestParam String expenseId){
        List<Expense> expenseList = service.getExpense(expenseId);
        return new ResponseEntity<>(expenseList.toString(),HttpStatus.OK);
    }

    @PutMapping("/update-expense/{expenseId}")
    public ResponseEntity<String> updateExpense(@RequestBody ExpenseDto updateExpense, @PathVariable Integer expenseId){
        JSONObject updateJson = new JSONObject(updateExpense);
        JSONObject errorList = validateExpense(updateJson);
        if(errorList.isEmpty()){
            return service.updateExpense(expenseId,updateJson);
        }
        return new ResponseEntity<>(errorList.toString(),HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete-expense/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable Integer expenseId){
        return service.deleteExpense(expenseId);
    }

    @GetMapping("/get-expenditure-by-userId/{userId}")
    public ResponseEntity<String> getExpenditure(@PathVariable int userId){
        long sum = service.getExpenditure(userId);
        return new ResponseEntity<>("Total expenditure - "+sum,HttpStatus.OK);
    }

    @GetMapping("/getMonthlyExpense/{userId}/{month}")
    public ResponseEntity<String> getMonthlyExpense(@PathVariable int userId,@PathVariable int month){
        long sum = service.getMonthlyExpense(userId,month);
        List<User> users = userDao.findUserById(userId);
        User user = users.get(0);
        return new ResponseEntity<>("Monthly expense of "+user.getFirstName()+" is - "+sum+"INR",HttpStatus.OK);
    }

    private Expense setExpense(JSONObject requestJson) {
        Expense expense = new Expense();
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        expense.setDate(createdDate);
        expense.setDescription(requestJson.getString("description"));
        expense.setPrice(requestJson.getInt("price"));
        expense.setTitle(requestJson.getString("title"));
        User user = userDao.findById(requestJson.getInt("user_id")).get();
        expense.setUser(user);
        Status status = statusDao.findById(1).get();
        expense.setStatus(status);
        return expense;
    }

    private JSONObject validateExpense(JSONObject requestJson) {
        JSONObject errorList = new JSONObject();
        if(requestJson.has("user_id")){
            Integer user_id = requestJson.getInt("user_id");
            if(userDao.findById(user_id).isEmpty() || !CommonUtils.onlyDigits(user_id.toString())) {
                errorList.put("Invalid parameter","user_id");
            }
        }else {
            errorList.put("Missing parameter","user_id");
        }
        if(!requestJson.has("price")){
            errorList.put("Missing parameter","price");
        }
        return errorList;
    }
}
