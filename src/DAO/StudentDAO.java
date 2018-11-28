package DAO;

import Student.Student;

import java.util.List;

public interface StudentDAO {
    public List<Student> findAllStudentInCourse(int courseID) throws Exception;
    // find all student in a course
    public Student findOneStudentInCourse(int courseID, String studentID) throws Exception;
    // find one student in a course
    public void addStudentToCourse(Student s) throws Exception;
    // add student to course (student should be exist in database otherwise call insertStudent() first)
    public void removeStudentFromCourse(Student s) throws Exception;
    // remove student from course
    public void insertStudent(Student s) throws Exception;
    // add a student to database
    public void updateStudent(Student s) throws Exception;
    // update student basic information (type,name)
    public void deleteStudent(Student s) throws Exception;
    // delete student from database
}
