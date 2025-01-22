package com.example.spring_mvc_demo.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    Long userId;
    String username;
    String email;
    String phoneNumber;
    String firstName;
    String lastName;
    Long departmentId;
    Integer status;
    Date createdTime;
    Date updatedTime;
    Long createdUser;
    Long updatedUser;
}
