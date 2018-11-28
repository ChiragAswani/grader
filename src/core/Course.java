package core;

import java.util.ArrayList;

public class Course {
    public ArrayList<Student> students;

    public Course(){
        this.students = new ArrayList<>();
    }
    public Course(ArrayList<Student> students){
        this.students = students;
    }

    public void addStudent(){

    }
}
