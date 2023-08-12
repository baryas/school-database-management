package com.data.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "student_table")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer studentId;

    private String firstName;

    private String lastName;

    private String email_Id;

    @Embedded
    private Guardian guardian;


    @ManyToMany(mappedBy = "students")
    private List<Course> courses = new ArrayList<>();
}
