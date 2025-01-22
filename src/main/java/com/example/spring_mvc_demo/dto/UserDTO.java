package com.example.spring_mvc_demo.dto;

import com.example.spring_mvc_demo.helper.valid.ValidName;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserDTO {


//    @Size(min = 6, max = 50, message = "Name must be between 6 and 50 characters")
//    @Pattern(regexp = "^ITS.*", message = "Username must start with 'ITS'")
    @ValidName
    String username;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain at least one letter and one digit")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;
    @Email(message = "Invalid email format")
    String email;
    @Pattern(regexp = "\\d{10}", message = "Must be 10 digit")
    String phoneNumber;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    Long departmentId;
}
