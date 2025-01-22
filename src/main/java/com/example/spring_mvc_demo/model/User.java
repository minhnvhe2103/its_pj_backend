package com.example.spring_mvc_demo.model;

import com.example.spring_mvc_demo.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "USERS", schema = "COMMON")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "COMMON.USERS_SEQ", allocationSize = 1)
    @Column(name = "USER_ID", nullable = false)
    Long userId;

    @Column(name = "USERNAME", length = 500)
    String username;
    @Column(name = "PASSWORD", length = 500, nullable = false)
    String password;
    @Column(name = "EMAIL", length = 500)
    String email;

    @Column(name = "PHONE_NUMBER", length = 50)
    String phoneNumber;

    @Column(name = "FIRST_NAME", length = 100)
    String firstName;

    @Column(name = "LAST_NAME", length = 100)
    String lastName;
//    @Column(name = "DEPARTMENT_ID", nullable = false)
//    Long departmentId;
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID")
    Department department;

    @Column(name = "STATUS")
    Integer status;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private Set<UserRole> userRoles;
}