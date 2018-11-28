package core;
import DAO.StudentDAO;
import Student.Student;
import database.StudentDB;
import grades.Gradable;
import grades.Grade;

import java.math.BigDecimal;
import java.util.List;


public class Course {

    private List<Student> studentList;
    private List<Gradable> gradableList;
    private String courseName;
    private String courseID;

    public Course(){

    }

    public Course(String ID,String name,List<Student> sList,List<Gradable> gList){
        courseID=ID;
        courseName=name;
        studentList=sList;
        gradableList=gList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Gradable> getGradableList() {
        return gradableList;
    }

    public void setGradableList(List<Gradable> gradableList) {
        this.gradableList = gradableList;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    // Add student to class from on click event in the UI
    public void addStudent(String studentID,String firstName,String lastName, boolean customWeights){
        Student newStudent = new Student(courseID, studentID, firstName, lastName, customWeights);
        studentList.add(newStudent);
        StudentDAO sdb=new StudentDB();
        try{
            sdb.addStudentToCourse(newStudent);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
