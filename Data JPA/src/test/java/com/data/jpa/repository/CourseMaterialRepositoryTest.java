package com.data.jpa.repository;

import com.data.jpa.entity.Course;
import com.data.jpa.entity.CourseMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseMaterialRepositoryTest {

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @Test
    public void saveCourseMaterial(){

        Course courseDetails = Course.builder()
                .title("IT")
                .credit(5)
                .build();

        CourseMaterial courseMaterial = CourseMaterial.builder()
                .course(courseDetails)
                .url("IT.com")
                .build();

        courseMaterialRepository.save(courseMaterial);
    }

    @Test
    public void printALlCourses(){
     List<CourseMaterial> list = courseMaterialRepository.findAll();
        System.out.println("list = " + list);

    }

}