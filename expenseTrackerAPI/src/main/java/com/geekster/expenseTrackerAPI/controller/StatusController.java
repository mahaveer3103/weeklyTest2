package com.geekster.expenseTrackerAPI.controller;
import com.geekster.expenseTrackerAPI.model.Status;

import com.geekster.expenseTrackerAPI.service.StatusService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {

    @Autowired
    StatusService statusService;

    @PostMapping("/create-status")
    public ResponseEntity<String> createStatus(@RequestBody Status statusData){
        Status status = setStatus(statusData);
        int id = statusService.saveStatus(status);
        return new ResponseEntity<>("status saved - "+id, HttpStatus.CREATED);
    }

    private Status setStatus(Status statusData) {
        Status status = new Status();
        JSONObject json = new JSONObject(statusData);
        String statusName = json.getString("statusName");
        String statusDescription = json.getString("statusDescription");

        status.setStatusName(statusName);
        status.setStatusDescription(statusDescription);

        return status;
    }

    @PutMapping("/update-status/{statusId}")
    public ResponseEntity<String> updateStatus(@PathVariable int statusId, @RequestBody Status requestStatus){
        Status status = setStatus(requestStatus);
        statusService.updateStatus(status,statusId);
        return new ResponseEntity<>("status updated successfully",HttpStatus.OK);
    }


}
