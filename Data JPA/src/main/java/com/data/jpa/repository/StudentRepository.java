package com.data.jpa.repository;

import com.data.jpa.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT s.firstName FROM Student s WHERE s.firstName like %?1% ORDER BY s.firstName")
    public List<String> getByFirstNameContainingOrderBy(String contains);

    @Query("SELECT DISTINCT  s.guardian.name FROM Student s WHERE s.firstName = ?1 OR s.lastName =?2 ")
    public List<String> getByDistinctGuardianNameByFirstNameOrLastName(String firstName, String lastName);


    @Query("SELECT s.firstName from Student s Where UPPER(s.firstName) = UPPER(?1) ")
    public List<String> getByFirstNameIgnoreCase(String contains);


    @Modifying
    @Transactional
    @Query("update Student s set s.firstName = ?1 where s.lastName = ?2")
    int updateStudentFirstNameByEmailAddress(String firstName, String lastName);


    @Modifying
    @Transactional
    @Query("delete from Student s where s.firstName = ?1")
    void deleteStudentByFirstName (String firstName);
}
