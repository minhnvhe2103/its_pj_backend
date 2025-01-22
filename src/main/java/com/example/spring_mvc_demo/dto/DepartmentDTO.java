package com.example.spring_mvc_demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class DepartmentDTO {
    @NotNull
    String DepartmentName;
    @NotNull
    String DepartmentCode;
    @NotNull
    Long parentDepartmentId;
}
