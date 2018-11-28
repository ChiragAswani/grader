package Student;

import grades.Grade;

import java.util.List;

public class Student {
    private int courseID;
    private String studentID;
    private String firstName;
    private String lastName;
    private int customized;
    private List<Grade> gradeList;
    private String type;
    public Student(){}
    public Student(int cID,String ID,String FN,String LN, int c,List<Grade> g){
        courseID=cID;
        studentID=ID;
        firstName=FN;
        lastName=LN;
        customized=c;
        gradeList=g;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
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

    public int getCustomized() {
        return customized;
    }

    public void setCostomized(int costomized) {
        this.customized = costomized;
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
