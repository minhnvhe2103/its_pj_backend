package com.example.spring_mvc_demo.model;

import com.example.spring_mvc_demo.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;


@Entity
@Table(name = "DEPARTMENT", schema = "COMMON")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Department extends BaseEntity {

    @Id
    @Column(name = "DEPARTMENT_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dpm_seq")
    @SequenceGenerator(name = "dpm_seq", sequenceName = "COMMON.DEPARTMENT_SEQ", allocationSize = 1)
    private Long departmentId;

    @Column(name = "DEPARTMENT_CODE", length = 100)
    private String departmentCode;

    @Column(name = "DEPARTMENT_NAME", length = 500)
    private String departmentName;

    @Column(name = "PARENT_DEPARTMENT_ID")
    private Long parentDepartmentId;

    @Column(name = "STATUS")
    private Integer status;

//    @OneToMany(mappedBy = "department")
//    private Set<User> users;
}
