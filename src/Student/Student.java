package Student;

import grades.Grade;

import java.util.List;

public class Student {
    private String courseID;
    private String studentID;
    private String firstName;
    private String lastName;
    private int costomized;
    private List<Grade> gradeList;
    private String type;
    public Student(){}
    public Student(String cID,String ID,String FN,String LN, int c,List<Grade> g){
        courseID=cID;
        studentID=ID;
        firstName=FN;
        lastName=LN;
        costomized=c;
        gradeList=g;
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

    public int getCostomized() {
        return costomized;
    }

    public void setCostomized(int costomized) {
        this.costomized = costomized;
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
