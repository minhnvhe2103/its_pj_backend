package com.example.spring_mvc_demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDetailResponse {
    String departmentCode;
    String departmentName;
    List<UserResponse> userResponses;
}
