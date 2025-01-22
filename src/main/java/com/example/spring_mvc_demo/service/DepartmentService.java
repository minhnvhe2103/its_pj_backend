package com.example.spring_mvc_demo.service;

import com.example.spring_mvc_demo.dto.UserUpdateRequest;
import com.example.spring_mvc_demo.dto.response.DepartmentDetailResponse;
import com.example.spring_mvc_demo.dto.response.DepartmentResponse;
import com.example.spring_mvc_demo.dto.response.UserResponse;
import com.example.spring_mvc_demo.helper.exception.AppException;
import com.example.spring_mvc_demo.helper.exception.ErrorCode;
import com.example.spring_mvc_demo.model.User;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Service;

import com.example.spring_mvc_demo.dto.DepartmentDTO;
import com.example.spring_mvc_demo.model.Department;
import com.example.spring_mvc_demo.repository.DepartmentRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService {
    DepartmentRepository departmentRepository;
    UserService userService;
    @NonFinal
    private Map<Long, Department> departmentCache;
    @NonFinal
    private Map<Long, DepartmentResponse> departmentResponseCache;

    private Map<Long, Department> getAllDepartment() {
        if (departmentCache == null) {
            List<Department> departments = departmentRepository.getAllDepartment();
            departmentCache = departments.stream()
                    .collect(Collectors.toMap(Department::getDepartmentId, department -> department));
        }
        return departmentCache;
    }

    public Map<Long, DepartmentResponse> getAllDepartmentResponse() {
        if (departmentResponseCache == null) {
            try {
                Map<Long, Department> departmentMap = getAllDepartment();

                departmentResponseCache = departmentMap.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> {
                                    Department department = entry.getValue();
                                    return DepartmentResponse.builder()
                                            .departmentId(department.getDepartmentId())
                                            .departmentCode(department.getDepartmentCode())
                                            .departmentName(department.getDepartmentName())
                                            .parentDepartmentId(
                                                    department.getDepartmentId() == 201
                                                            ? null
                                                            : department.getParentDepartmentId()
                                            )
                                            .status(department.getStatus())
                                            .build();
                                }
                        ));
            } catch (Exception e) {
                throw new RuntimeException("Loi o dau roi", e);
            }
        }
        return departmentResponseCache;
    }

    public DepartmentResponse getDepartmentResponseByID(Long departmentID) {
        DepartmentResponse departmentResponse = getAllDepartmentResponse().get(departmentID);
        if (departmentResponse == null) {
            throw new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED);
        }
        return departmentResponse;
    }


    private Department getByDepartmentID(Long id) {
        Department department = getAllDepartment().get(id);

        if (department == null) {
            throw new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED);
        }

        return department;
    }

    public DepartmentDetailResponse viewDetailDepartmentById(Long id) {
        DepartmentResponse departmentResponse = getDepartmentResponseByID(id);
        List<UserResponse> userResponses = userService.getAllUser().stream()
                .filter(userResponse -> departmentResponse.getDepartmentId() != null &&
                        departmentResponse.getDepartmentId().equals(departmentResponse.getDepartmentId()))
                .map(userResponse -> UserResponse.builder()
                        .userId(userResponse.getUserId())  // Sử dụng userId thay vì userID
                        .email(userResponse.getEmail())
                        .phoneNumber(userResponse.getPhoneNumber())
                        .firstName(userResponse.getFirstName())
                        .lastName(userResponse.getLastName())
                        .departmentId(departmentResponse.getDepartmentId())
                        .status(userResponse.getStatus())
                        .username(userResponse.getUsername())
                        .createdTime(userResponse.getCreatedTime())
                        .updatedTime(userResponse.getUpdatedTime())
                        .createdUser(userResponse.getCreatedUser())
                        .updatedUser(userResponse.getUpdatedUser())
                        .build())
                .collect(Collectors.toList());  // Chuyển từ Set sang List

        return DepartmentDetailResponse.builder()
                .departmentCode(departmentResponse.getDepartmentCode())
                .departmentName(departmentResponse.getDepartmentName())
                .userResponses(userResponses)
                .build();
    }




    public DepartmentResponse updateStatusDepartment(Long id, Integer status) {
        Department department = getByDepartmentID(id);

        department.setStatus(status);
        department.setUpdatedTime(new Date());
        department.setUpdatedUser(1L);

        return DepartmentResponse.builder()
                .departmentCode(department.getDepartmentCode())
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .status(department.getStatus())
                .parentDepartmentId(department.getParentDepartmentId())
                .build();
    }
    
}
