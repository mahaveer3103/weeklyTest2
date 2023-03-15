package com.geekster.expenseTrackerAPI.service;

import com.geekster.expenseTrackerAPI.dao.StatusDao;
import com.geekster.expenseTrackerAPI.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    StatusDao statusRepository;

    public int saveStatus(Status status) {
        return statusRepository.save(status).getStatusId();
    }

    public void updateStatus(Status status, int statusId) {
        status.setStatusId(statusId);
        statusRepository.save(status);
    }
}
