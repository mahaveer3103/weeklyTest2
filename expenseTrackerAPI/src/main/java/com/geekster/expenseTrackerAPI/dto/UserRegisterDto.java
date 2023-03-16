package com.geekster.expenseTrackerAPI.dto;

import lombok.Data;

@Data
public class UserRegisterDto {
        private String username;
        private String password;
        private String first_name;
        private String last_name;
        private Integer age;
        private String email_id;
        private String phone_number;
}
