package com.geekster.expenseTrackerAPI.dao;

import com.geekster.expenseTrackerAPI.model.Expense;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseDao extends JpaRepository<Expense,Integer> {

    @Query(value = "select * from tbl_expense where expense_id=:expenseId and status_id = 1",nativeQuery = true)
    List<Expense> findExpenseById(Integer expenseId);

    @Query(value = "select * from tbl_expense where status_id = 1",nativeQuery = true)
    List<Expense> findAllExpense();

    @Modifying
    @Transactional
    @Query(value = "update tbl_expense set status_id=2 where expense_id= :expenseId",
            countQuery = "Select count(*) from tbl_expense",nativeQuery = true)
    void deleteExpenseById(@PathParam("expenseId") int expenseId);

    @Query(value = "select sum(price) from tbl_expense where user_id = :userId and status_id=1",nativeQuery = true)
    long getExpenseByUserId(int userId);

    @Query(value = "select sum(price) from tbl_expense where user_id =:userId and month(date) =:month and status_id=1",nativeQuery = true)
    long getMonthlyExpenseByUserId(int userId, int month);
}
