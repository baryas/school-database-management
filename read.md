# School Database Management System



Project Architecture : 

![Screenshot 2023-08-12 at 3 18 11 PM](https://github.com/baryas/school-database-management/assets/141733993/35748687-2c52-4080-a675-cd82431b439b)

Project Description : 

I am kindly trying to present here a model of student database management system. I use ORM (Object Relational Mapping) with Hibernate. 

Relationship:


In the model, We have 4 entities - Student, Course, Course Material and Teacher.

Each Course has their own Course Material. Hence there is one to one relationship between Course and Course Material. 

Between Teacher and Course, There is one to many relationship, One teacher can teach many courses.

Between Student and Course, There is many to many relationship, Many Students can take Many Courses. Hence to normalize this, We are using student_course_map to show this relationship.
Technologies and Frameworks:

Here, I used ORM’s Hibernate framework to map Object - Relational Database Mapping, With the help of Maven build tool and Spring Boot. I used JUnit5 as a testing framework. DataBase used for this project is MySQL. 


Adding necessary dependencies to our POM.XML file: 


 <dependency> 
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
   <groupId>com.mysql</groupId>
   <artifactId>mysql-connector-j</artifactId>
   <scope>runtime</scope>
</dependency>
 <dependency>
   <groupId>org.projectlombok</groupId>
   <artifactId>lombok</artifactId>
   <optional>true</optional>
</dependency>
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-test</artifactId>
   <scope>test</scope>
</dependency>
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-web</artifactId>
</dependency>
Above data-jpa is using Hibernates as it’s default implementation.


<dependency>
	<groupId>org.hibernate.orm</groupId>
	 <artifactId>hibernate-core</artifactId>
 	<version>6.1.7.Final</version>
	<scope>compile</scope>
</dependency>

Connecting Application to Database : 

Application is connected to MySQL Database.In application.properties, I defined data source properties below to create a bridge between Application and database.


spring.datasource.url=jdbc:mysql://localhost:3306/school_db
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name =com.mysql.jdbc.Driver
spring.jpa.show-sql: true
spring.jpa.hibernate.ddl-auto=update



school_db is schema in database, which I also create in database manually : 

CREATE SCHEMA `school_db`

Packages  : 
Com.data.jpa  has two packages- entity and repository. In entity package we have 5 java classes (POJO Classes). In repository package, we have 4 repository interfaces.  

Student.Java

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



Above I used annotations based mapping.
 @Entity annotation from ‘package com.data.jpa.entity’ to mark this class as Entity in database. It is mapped with “student_table”.  
I used Lombok framework’s @Data annotation to include all boiler-plate code ( getter-setter, to-string method, constructor etc. )

@Id annotation is to map primary key of that particular class. @GeneratedValue annotation is used to generate id each time we add row to tables.

@Embedded annotation is used because Guardian class is embedded inside Student class representing Guardian is not separate entity but part of Student entity.  @Embeddable is used for Guardian class below. 
@Column Annotation is used to map properties of class to column of the Table.
Guardian.java

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Embeddable
public class Guardian {


   @Column(name = "guardian_name")
   private String name;


   @Column(name = "guardian_email")
   private String email;


   @Column(name = "guardian_mobile")
   private String mobile;


}

Similarly, I mapped following POJO classes and their properties to database using annotations.

Teacher.java

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {


   @Id
   @SequenceGenerator(
           name = "teacher_sequence",
           sequenceName = "teacher_sequence",
           allocationSize = 1)
   @GeneratedValue(
           generator = "teacher_sequence",
           strategy = GenerationType.SEQUENCE
   )
   private Long teacherId;
   @Column(name = "first_name")
   private String firstName;
  
   @Column(name = "last_name")
   private String lastName;


Course.java
@Entity
@Data
@AllArgsConstructor
@Builder
public class Course {


   @Id
   @SequenceGenerator(
           name = "course_sequence",
           sequenceName = "course_sequence",
           allocationSize = 1
   )
   @GeneratedValue(
           strategy = GenerationType.SEQUENCE,
           generator = "course_sequence"
   )
   private Long courseId;


   private String title;


   private Integer credit;

CourseMaterial.java

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "course")
public class CourseMaterial {


   @Id
   @SequenceGenerator(
           name = "course_material_sequence",
           sequenceName = "course_material_sequence",
           allocationSize = 1
   )
   @GeneratedValue(
           strategy = GenerationType.SEQUENCE,
           generator = "course_sequence_generator"
   )
   private Long courseMaterialId;
   private String url;

Repositories for each POJO classes : 

Under com.data.jpa I created a new package called repository and created 4 repository interfaces StudentRepository, CourseRepository, CourseMaterialRepository, TeacherRepository. I used @Repository annotation to indicate this as Repository.

StudentRepository.java

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {


TeacherRepository.java

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {


CourseRepository.java


@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

CourseMaterialRepository:



@Repository
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial,Long> {
}

Defining Relationship between entities : 

Course and Course Material : 

Course and CourseMaterial have one-to-one relationship. So, In CourseMaterial.java,  I created variable of Course and added @OneToOne with @JoinColumn. 



@OneToOne(
       cascade = CascadeType.ALL
)
@JoinColumn(
       name = "course_id",
       referencedColumnName = "courseId"
)
private Course course;


Course and Teacher : 

It has Many to one or one to Many relationship. Many courses can be taught by teacher or teacher can teach many courses. 
Hence, In Course.java I created variable of Teacher and  added @ManyToOne relationship. 



@ManyToOne(
       cascade = CascadeType.PERSIST
)
@JoinColumn(
       name = "teacher_id",
       referencedColumnName = "teacherId"
)
private Teacher teacher;



Student and Course : 

Here, Relationship is Many to Many because many students can have Many courses.
In Course.java, I created variables students and added @ManyToMany relationship. 
I also used @JoinTable to map courseId and studentId.



@ManyToMany (cascade = CascadeType.ALL)
@JoinTable(
       name = "student_course_map",
       joinColumns = @JoinColumn(
               name = "course_id",
               referencedColumnName = "courseId"
       ),
       inverseJoinColumns = @JoinColumn(
               name = "student_id",
               referencedColumnName = "studentId"
       )
)
private List<Student> students ;
 


Testing our repository methods : 

StudentRepositoryTest.java 

Here I used @SpringBootTest and injected studentRepository to call repository’s methods. 



@SpringBootTest
class StudentRepositoryTest {




   @Autowired
   private StudentRepository studentRepository;


All methods implemented below are defined in our repository interface. I used @Test annotation methods. I used .builder().build() to save students into Database. 








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

Testing query method : Find Student By First Name Containing

StudentRepository.java


@Query("SELECT s.firstName FROM Student s WHERE s.firstName like %?1% ORDER BY s.firstName")
public List<String> getByFirstNameContainingOrderBy(String contains);


StudentRepositoryTest.java
@Test
public void printStudentFirstNameContains(){
   List<String> list =
           studentRepository.getByFirstNameContainingOrderBy("a");


   System.out.println("list = " + list);
}

Testing query  method : print guardian name by Student’s first name or last Name

StudentRepository.java
@Query("SELECT DISTINCT  s.guardian.name FROM Student s WHERE s.firstName = ?1 OR s.lastName =?2 ")
public List<String> getByDistinctGuardianNameByFirstNameOrLastName(String firstName, String lastName);

StudentRepositoryTest.java


@Test
public void printDistinctGuardianName(){
   List<String> gurardianName =
           studentRepository.getByDistinctGuardianNameByFirstNameOrLastName("Priya", "kumar");


   System.out.println("gurardianName = " + gurardianName);
}


Testing method: print Students by First Name (Ignore case)

StudentRepository.java
@Query("SELECT s.firstName from Student s Where UPPER(s.firstName) = UPPER(?1) ")
public List<String> getByFirstNameIgnoreCase(String contains);


StudentRepositoryTest.java
@Test
public void printStudentFirstNameIgnoreCase(){
   List<String> name =
           studentRepository.getByFirstNameIgnoreCase("priya");


   System.out.println("list = " + name);


}

Testing method: Update Student First Name By Last name 

StudentRepository.java
@Modifying
@Transactional
@Query("update Student s set s.firstName = ?1 where s.lastName = ?2")
int updateStudentFirstNameByEmailAddress(String firstName, String lastName);




StudentRepositoryTest.java

@Test
public void updateStudentFirstNameByLastName(){
   studentRepository.updateStudentFirstNameByEmailAddress("lallu","ghanshyam");
}

Testing method : Delete Student By first name

StudentRepository.java


   @Modifying
   @Transactional
   @Query("delete from Student s where s.firstName = ?1")
   void deleteStudentByFirstName (String firstName);
}


StudentRepositoryTest.java
@Test
public void deleteStudentsWithGuardian() {
   studentRepository.deleteStudentByFirstName("Lallu");
}


CourseRepositoryTest.java 

Testing Method : To see all course : 



@Test
public void printAllCourses() {
List<Course> list = courseRepository.findAll();
   System.out.println("list = " + list);
}


Testing  Method: Save courses with Teacher




@Test
public void saveCoursesWithTeacher(){


   Teacher teacherDetail = Teacher.builder()
           .firstName("Ramesh")
           .lastName("Sharma")
           .build();


   Course course1 = Course.builder()
           .title("JavaEE")
           .credit(4)
           .teacher(teacherDetail)
           .build();




   courseRepository.save(course1);


}


Testing Method : Save courses- Students

Below I used again .builder() method to insert teacher, students data while saving course.



@Test
public void saveCourseWithStudentAndTeacher(){
   Teacher teacherDetails = Teacher.builder()
           .firstName("abhinav")
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


To make this description more readable and reduce the redundancy , I have not included all (Such as paginations, some methods) which I tested as well. All test methods were successfully passed.  
