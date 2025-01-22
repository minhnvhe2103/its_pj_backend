package com.example.spring_mvc_demo.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE)
public class LoginResponse {
    boolean isAuth;
}
