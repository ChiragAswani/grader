package DAO;

import Student.Student;

import java.util.List;

public interface StudentDAO {
    public List<Student> findAllStudentInCourse(int courseID) throws Exception;
    public Student findOneStudentInCourse(int courseID, String studentID) throws Exception;
    public void addStudentToCourse(Student s) throws Exception;
    public void removeStudentFromCourse(Student s) throws Exception;
    public void insertStudent(Student s) throws Exception;
    public void updateStudent(Student s) throws Exception;
    public void deleteStudent(Student s) throws Exception;
}
