package com.example.spring_mvc_demo.model.base;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@MappedSuperclass
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_TIME")
    Date updatedTime;

    @Column(name = "CREATED_USER")
    Long createdUser;

    @Column(name = "UPDATED_USER")
    Long updatedUser;
}