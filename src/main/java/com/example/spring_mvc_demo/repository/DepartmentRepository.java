package com.example.spring_mvc_demo.repository;

import com.example.spring_mvc_demo.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    @Query(value = "SELECT * FROM COMMON.DEPARTMENT d WHERE d.DEPARTMENT_ID = :departmentId", nativeQuery = true)
    Department findDepartmentById(@Param("departmentId") Long departmentId);
    @Query(value = "SELECT * FROM department d", nativeQuery = true)
    List<Department> getAllDepartment();
//    @Query(value = "SELECT * FROM COMMON.DEPARTMENT d WHERE d.DEPARTMENT_ID = :departmentId", nativeQuery = true)
//    Department findDepartmentByCode(@Param("departmentId") String  departmentCode);
}
