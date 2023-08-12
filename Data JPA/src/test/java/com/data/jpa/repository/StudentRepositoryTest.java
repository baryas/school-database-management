package com.data.jpa.repository;

import com.data.jpa.entity.Guardian;
import com.data.jpa.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentRepositoryTest {


    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveStudent(){

        Guardian guardianDetails = Guardian.builder()
                .name("Satyam")
                .mobile("495868")
                .email("satyam@gmail.com")
                .build();

        Student student = Student.builder()
                .firstName("Tinku")
                .lastName("kumar")
                .guardian(guardianDetails)
                .build();
        studentRepository.save(student);

    }
    
    @Test
    public void printStudentFirstNameContains(){
        List<String> list =
                studentRepository.getByFirstNameContainingOrderBy("a");

        System.out.println("list = " + list);
    }

    @Test
    public void printDistinctGuardianName(){
        List<String> gurardianName =
                studentRepository.getByDistinctGuardianNameByFirstNameOrLastName("Priya", "kumar");

        System.out.println("gurardianName = " + gurardianName);
    }

    @Test
    public void printStudentFirstNameIgnoreCase(){
        List<String> name =
                studentRepository.getByFirstNameIgnoreCase("priya");

        System.out.println("list = " + name);

    }

    @Test
    public void updateStudentFirstNameByLastName(){
        studentRepository.updateStudentFirstNameByEmailAddress("lallu","ghanshyam");
    }

    @Test
    public void deleteStudentsWithGuardian() {
        studentRepository.deleteStudentByFirstName("Lallu");
    }


}
