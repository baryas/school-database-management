package com.data.jpa.repository;

import com.data.jpa.entity.Course;
import com.data.jpa.entity.Student;
import com.data.jpa.entity.Teacher;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.sql.Array;
import java.sql.ClientInfoStatus;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {
    
    @Autowired
    private CourseRepository courseRepository;

    
    @Test
    public void printAllCourses() {
     List<Course> list = courseRepository.findAll();
        System.out.println("list = " + list);
    }

    @Test
    public void saveCoursesWithTeacher(){

        Teacher teacherDetail = Teacher.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .build();

        Course course1 = Course.builder()
                .title("JavaEE")
                .credit(4)
                .teacher(teacherDetail)
                .build();


        courseRepository.save(course1);

    }


   @Test
    public void findAllPagination(){
        Pageable firstPageWithThreeRecords =
                PageRequest.of(0,3);

        Pageable secondPageWithTwoRecords =
                PageRequest.of(1,2);
        List<Course> page1 =
                courseRepository.findAll(firstPageWithThreeRecords).getContent();
        // retrieving Second page
       List<Course> page2 =
               courseRepository.findAll(secondPageWithTwoRecords).getContent();

       System.out.println("page2 = " + page2);
        System.out.println("list = " + page1);

    }

    @Test
    public void findAllPaginationElements(){
        Pageable firstPageTotalElements =
                PageRequest.of(0,2);

        Pageable secondPageWithTwoRecords =
                PageRequest.of(1,2);

        // to see total elements (total rows)
        long totalElements =
                courseRepository.findAll(firstPageTotalElements).getTotalElements();

        System.out.println("firstPageElements = " + totalElements);

        //to see how many pages
        long totalPages =
                courseRepository.findAll(firstPageTotalElements).getTotalPages();

        System.out.println("totalPages = " + totalPages);
    }

@Test
    public void findAllWithSorting(){
        Pageable firstPage = PageRequest.of(
                0,
                2,
                Sort.by("title"));

       List<Course>  SortPageByTitle = courseRepository.findAll(firstPage).getContent();

    System.out.println("SortPageByTitle = " + SortPageByTitle);
}
    @Test
    public void findAllWithSortingAndAscending(){
        Pageable firstPageSortWithCredit = PageRequest.of(0,5,
                Sort.by("credit").ascending());
        List<Course> list =
                courseRepository.findAll(firstPageSortWithCredit).getContent();

        System.out.println("list = " + list);

    }

    @Test
    public void printFindByTitleContaining(){
        Pageable firstPage5Records = PageRequest.of(0,5);

        List<Course> list =
                courseRepository.findByTitleContaining("a",firstPage5Records).getContent();

        System.out.println("list = " + list);
    }

    //Many to Many

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void saveCourseWithStudentAndTeacher(){
        Teacher teacherDetails = Teacher.builder()
                .firstName("Shabbir")
                .lastName("Daawoodi")
                .build();

        Student student1 = Student.builder()
                .firstName("Raju")
                .lastName("chacha")
                .email_Id("Raju@gmail.com")
                .build();

        Student student2= Student.builder()
                .firstName("kaka")
                .lastName("kher")
                .email_Id("kaka@gmail.com")
                .build();


        Course course1 = Course.builder()
                .title("English2")
                .credit(4)
                .teacher(teacherDetails)
                .build();

        course1.addStudent(student1);

        courseRepository.save(course1);

    }

}