package com.example.spring_mvc_demo.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserListResponse {

    Long userId;
    String email;
    String phoneNumber;
    String firstName;
    String lastName;
    Long departmentId;
    Integer status;
}
