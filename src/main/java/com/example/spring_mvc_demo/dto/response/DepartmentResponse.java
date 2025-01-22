package com.example.spring_mvc_demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponse {
    Long departmentId;
    String departmentCode;
    String departmentName;
    Long parentDepartmentId;
    Integer status;
}
