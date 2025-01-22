package com.example.spring_mvc_demo.dto;

import com.example.spring_mvc_demo.helper.valid.ValidName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserUpdateRequest {
    @Email(message = "Invalid email format")
    String email;
    @Pattern(regexp = "\\d{10}", message = "Must be 10 digit")
    String phoneNumber;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
}
