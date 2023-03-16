package com.geekster.expenseTrackerAPI.dto;

import lombok.Data;

@Data
public class ExpenseDto {
    private String title;
    private String description;
    private String price;
    private String user_id;
}
