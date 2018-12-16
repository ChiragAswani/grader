package Student;

import DAO.GradeDAO;
import database.GradeDB;
import grades.Grade;
import grades.Tag;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private int courseID;
    private String studentID;
    private String firstName;
    private String lastName;
    private List<Grade> gradeList;
    private String type;

    private int customized;
    private List<BigDecimal> customWeights;

    public Student(){}
    public Student(int cID,String ID,String FN,String LN, int c,List<Grade> g){
        courseID=cID;
        studentID=ID;
        firstName=FN;
        lastName=LN;
        customized=c;
        gradeList=g;
    }
    public Student(int cID,String ID,String FN,String LN, int c, String type){
        courseID=cID;
        studentID=ID;
        firstName=FN;
        lastName=LN;
        customized=c;
        gradeList= new ArrayList<>();
        this.type=type;
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

    public void setCustomized(int customized) {
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

    public void editGrade(int gradableid,int index, BigDecimal newScore, List<Tag> tags){
        gradeList.get(index).settList(tags);
        gradeList.get(index).setScore(newScore);
        GradeDAO gdb = new GradeDB();
        try{
            if (gdb.findOneGrade(studentID,gradableid)!=null){
                gdb.updateGrade(gradeList.get(index));
            }else {
                gdb.insertGrade(gradeList.get(index));
            }
        }
        catch (Exception e){

        }
    }
}
