package com.geekster.expenseTrackerAPI.dao;

import com.geekster.expenseTrackerAPI.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusDao extends JpaRepository<Status,Integer> {

}
