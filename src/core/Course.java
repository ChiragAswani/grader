package core;

import DAO.GradableDAO;
import DAO.GradeDAO;
import DAO.StudentDAO;
import Student.Student;
import database.GradableDB;
import database.GradeDB;
import database.StudentDB;
import grades.Gradable;
import grades.Category;
import grades.Grade;
import grades.Tag;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class Course {

    private List<Student> studentList;
    private List<Gradable> gradableList;
    private List<Category> categoryList;
    private String courseName;
    private int courseID;
    private int archived=0;


    public Course(){
        studentList=new ArrayList<>();
        gradableList= new ArrayList<>();
    }

    public Course(int ID,String name,List<Student> sList,List<Gradable> gList){
        courseID=ID;
        courseName=name;
        studentList=sList;
        gradableList=gList;
    }

    public Course(int ID,String name,List<Student> sList,List<Gradable> gList,List<Category> cList){
        courseID=ID;
        courseName=name;
        studentList=sList;
        gradableList=gList;
        categoryList=cList;
    }

    public int getArchived() {
        return archived;
    }

    public void setArchived(int archived) {
        this.archived = archived;
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

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public boolean studentPresent(String studentID){
        for (Student student : studentList) {
            if (student.getStudentID().equals(studentID)){
                return true;
            }
        }
        return false;
    }

    public boolean assignmentPresent(String assignmentName, String categoryName){
        for (Gradable gradable : gradableList) {
            if (gradable.getAssignmentName().equals(assignmentName) && gradable.getType().equals(categoryName)){
                return true;
            }
        }
        return false;

    }

    public boolean assignmentPresent(String assignmentName){
        for (Gradable gradable : gradableList) {
            if (gradable.getAssignmentName().equals(assignmentName)){
                return true;
            }
        }
        return false;

    }



    // Add student to class from on click event in the UI
    public void addStudent(String studentID,String firstName,String lastName, int customWeights, String type){
        if (studentPresent(studentID)){
            editStudent(studentID, firstName, lastName,customWeights);
        }
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

    public void deleteStudent(String studentID){
        int index = findStudentIndex(studentID);
        StudentDAO sdb=new StudentDB();
        try{

            sdb.removeStudentFromCourse(studentList.get(index));
        }catch (Exception e){
            e.printStackTrace();
        }
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

    public int addGradable(String assignmentName, BigDecimal maxScore, BigDecimal weightU, BigDecimal weightG, int customized, String gradableCategory){
        if (assignmentPresent(assignmentName, gradableCategory)){
            return -1;
        }
        System.out.println("Adding new gradable to db");
//        Gradable gradable = new Gradable(courseID, gradableId, assignmentName, maxScore, weightU, weightG, c, t);
        Gradable newGradable = new Gradable();
        newGradable.setCourseID(courseID);
        newGradable.setAssignmentName(assignmentName);
        newGradable.setMaxScore(maxScore);
        newGradable.setWeight_grad(weightG);
        newGradable.setWeight_ungrad(weightU);
        newGradable.setType(gradableCategory);
        newGradable.setCustomized(customized);
        GradableDAO gdb = new GradableDB();
        try{
            Gradable builtGradable = gdb.addGradableToOneCourse(newGradable);
            gradableList.add(builtGradable);
            return  builtGradable.getgID();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public void removeGradable(int gradableID){
        GradableDAO gdb = new GradableDB();
        try{
           Gradable gradable = gdb.findOneGradableInCourse(courseID, gradableID);
           gdb.deleteGradable(gradable);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void editGrade(String studentID, String assignmentName, BigDecimal newScore, List<Tag> tags){
//        if (!assignmentPresent(assignmentName)){
//            addGradable(assignmentName, newScore, )
//        }
        System.out.println("Editing grade for student: "+studentID);
        System.out.println("Assignment: "+assignmentName+" and score: "+newScore);
        int studentIndex = findStudentIndex(studentID);
        int assignmentIndex = findAssignmentIndex(assignmentName);
        studentList.get(studentIndex).editGrade(gradableList.get(assignmentIndex).getgID(),assignmentIndex,newScore, tags);
    }

    public void createCategory(){

    }


    public HashMap<String, String> calculateFinalGrades(){
        GradeDAO gdb = new GradeDB();
        HashMap<String, String> finalScores = new HashMap<>();
        for (Student student : studentList) {
            BigDecimal totalWeight = new BigDecimal(0);
            BigDecimal totalScore = new BigDecimal(0);
            for (Gradable gradable : gradableList) {
               try{
                   Grade studentGrade = gdb.findOneGrade(student.getStudentID(), gradable.getgID());
                   BigDecimal studentScore = studentGrade.getScore();
                   BigDecimal weight = student.getType() == "grad" ? gradable.getWeight_grad() : gradable.getWeight_ungrad();
                   totalWeight.add(weight);
                   totalScore.add(studentScore.multiply(weight));
               }
               catch (Exception e){
                   e.printStackTrace();
               }
               String studentID = student.getStudentID();
               String finalScore = totalScore.divide(totalWeight).toString();
               finalScores.put(studentID, finalScore);
            }
        }
        return finalScores;
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
