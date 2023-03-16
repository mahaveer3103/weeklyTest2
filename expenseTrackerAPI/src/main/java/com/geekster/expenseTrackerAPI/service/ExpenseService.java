package com.geekster.expenseTrackerAPI.service;

import com.geekster.expenseTrackerAPI.dao.ExpenseDao;
import com.geekster.expenseTrackerAPI.dao.UserDao;
import com.geekster.expenseTrackerAPI.model.Expense;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    ExpenseDao dao;

    @Autowired
    UserDao userDao;

    public int addExpense(Expense expense) {
        return dao.save(expense).getExpenseId();
    }

    public List<Expense> getExpense(String expenseId) {
        if(expenseId != null){
            return dao.findExpenseById(Integer.valueOf(expenseId));
        }else {
            return dao.findAllExpense();
        }
    }

    public ResponseEntity<String> updateExpense(Integer expenseId, JSONObject updateJson) {
        if(!dao.findExpenseById(expenseId).isEmpty()){
            List<Expense> expenseList = dao.findExpenseById(expenseId);
            Expense ex = expenseList.get(0);
            setUpdateExpense(ex,updateJson);
            return new ResponseEntity<>("expense updated successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid expenseId", HttpStatus.BAD_REQUEST);
    }

    private void setUpdateExpense(Expense ex, JSONObject updateJson) {
        ex.setTitle(updateJson.getString("title"));
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        ex.setDate(createdDate);
        ex.setPrice(updateJson.getInt("price"));
        ex.setDescription(updateJson.getString("description"));
        dao.save(ex);
    }

    public ResponseEntity<String> deleteExpense(Integer expenseId) {
        if(!dao.findExpenseById(expenseId).isEmpty()){
            dao.deleteExpenseById(expenseId);
            return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid expense_id",HttpStatus.BAD_REQUEST);
    }

    public long getExpenditure(int userId) {
        long sum = dao.getExpenseByUserId(userId);
        return sum;
    }

    public long getMonthlyExpense(int userId, int month) {
        long sum = dao.getMonthlyExpenseByUserId(userId,month);
        return sum;
    }
}
