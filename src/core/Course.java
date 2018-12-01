package core;
import DAO.StudentDAO;
import Student.Student;
import database.StudentDB;
import grades.Gradable;
import grades.Grade;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


public class Course {

    private List<Student> studentList;
    private List<Gradable> gradableList;
    private String courseName;
    private int courseID;

    public Course(){

    }

    public Course(int ID,String name,List<Student> sList,List<Gradable> gList){
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

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    // Add student to class from on click event in the UI
    public void addStudent(String studentID,String firstName,String lastName, int customWeights, String type){
        Student newStudent = new Student(courseID, studentID, firstName, lastName, customWeights, type);
        studentList.add(newStudent);
        StudentDAO sdb=new StudentDB();
        try{
            sdb.addStudentToCourse(newStudent);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(studentList.toArray()));
    }

    public void editStudent(String studentID, String firstName, String lastName, int customWeights){
        int studentIndex = findStudentIndex(studentID);
        Student currentStudent = studentList.get(studentIndex);
        currentStudent.setFirstName(firstName);
        currentStudent.setLastName(lastName);
        currentStudent.setCustomized(customWeights);
        StudentDAO sdb=new StudentDB();
        try{
            sdb.updateStudent(currentStudent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addGradingCategory(int gradableId, String assignmentName, BigDecimal maxScore, BigDecimal weightU, BigDecimal weightG, int c, String t){
        Gradable gradable = new Gradable(courseID, gradableId, assignmentName, maxScore, weightU, weightG, c, t);
//        addGradableToOneCourse
    }


    public void editGrade(String studentID, String assignmentName, BigDecimal newScore){
        int studentIndex = findStudentIndex(studentID);
        int assignmentIndex = findAssignmentIndex(assignmentName);
        studentList.get(studentIndex).editGrade(assignmentIndex,newScore);

    }

    public int findStudentIndex(String studentID) {
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            if (student.getStudentID().equals(studentID)) {
                return i;
            }
        }
        return -1;
    }
    public int findAssignmentIndex(String assignmentName) {
        for (int i = 0; i < gradableList.size(); i++) {
            Gradable gradable = gradableList.get(i);
            if (gradable.getAssignmentName().equals(assignmentName)) {
                return i;
            }
        }
        return -1;
    }
}
