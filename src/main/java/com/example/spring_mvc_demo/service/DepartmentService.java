package com.example.spring_mvc_demo.service;

import org.springframework.stereotype.Service;

import com.example.spring_mvc_demo.dto.DepartmentDTO;
import com.example.spring_mvc_demo.model.Department;
import com.example.spring_mvc_demo.repository.DepartmentRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService {
    DepartmentRepository departmentRepository;

    public void addDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setDepartmentCode(departmentDTO.getDepartmentCode());
        department.setDepartmentName(departmentDTO.getDepartmentName());
        department.setParentDepartmentId(departmentDTO.getParentDepartmentId());
        department.setStatus(1);
        departmentRepository.save(department);
    }
    
}
