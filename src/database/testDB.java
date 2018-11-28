package database;

import DAO.*;
import Student.Student;
import core.Course;
import grades.Gradable;
import grades.Grade;
import grades.Tag;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class testDB {
    public static void main(String[] args) {
        StudentDAO sdb=new StudentDB();
        GradeDAO gdb=new GradeDB();
        CourseDAO cdb=new CourseDB();
        GradableDAO gadb=new GradableDB();


        try{
            Gradable gradable=new Gradable(1,-1,"test",new BigDecimal(100),null,null,0,"hw");
            gadb.removeGradableFromOneCourse(gradable);
        }catch (Exception e){
            e.printStackTrace();
        }

//        try{
//            Student s=sdb.findOneStudentInCourse("CS591E2","test3_U");
//            Tag t= new Tag(1,"abc");
//            Tag t2=new Tag(3,"ttt");
//            List<Tag> tlist=new ArrayList<Tag>();
//            tlist.add(t);
//            tlist.add(t2);
//            Grade g=new Grade(s.getStudentID(),5,new BigDecimal(200),new BigDecimal(0.40),tlist);
//            gdb.insertGrade(g);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }




    }
}
