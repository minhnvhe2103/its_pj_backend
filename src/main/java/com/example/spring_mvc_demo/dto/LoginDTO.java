package com.example.spring_mvc_demo.dto;

import com.example.spring_mvc_demo.helper.valid.ValidName;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDTO {
    @ValidName
    String username;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain at least one letter and one digit")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;
}
