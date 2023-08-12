package com.data.jpa.repository;

import com.data.jpa.entity.Course;
import com.data.jpa.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;


//    @Test
//    public void saveTeacherWithCourses(){
//
//        Course course1 = Course.builder()
//                .title("jpa")
//                .credit(5)
//                .build();
//        Course course2 = Course.builder()
//                .title("security")
//                .credit(5)
//                .build();
//
//
//        Teacher teacher = Teacher.builder()
//                .firstName("Shabbir")
//                .lastName("Daawoodi")
//                .courses(List.of(course1,course2))
//                .build();
////
////        teacherRepository.save(teacher);
////
////        System.out.println("teacher = " + teacher);
//
//    }
//
}