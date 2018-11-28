package Student;

import grades.Grade;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String courseID;
    private String studentID;
    private String firstName;
    private String lastName;
    private boolean customized;
    private List<Grade> gradeList;
    private String type;
    public Student(){}
    public Student(String cID,String ID,String FN,String LN, boolean c,List<Grade> g){
        courseID=cID;
        studentID=ID;
        firstName=FN;
        lastName=LN;
        customized=c;
        gradeList=g;
    }
    public Student(String cID,String ID,String FN,String LN, boolean c){
        courseID=cID;
        studentID=ID;
        firstName=FN;
        lastName=LN;
        customized=c;
        gradeList= new ArrayList<>();
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getCustomized() {
        return customized;
    }

    public void setCustomized(boolean customized) {
        this.customized = customized;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
