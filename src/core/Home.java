package core;

import DAO.CourseDAO;
import database.CourseDB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Home {
    List<Course> courses;

    public Boolean login(String pass){
        // Try auth
        // if username and password match return true else false
        return false;
    }

    public void changeLogin(String pass){
        //
    }
    public List<Integer> seeCourses(){
        //
        CourseDAO cdb = new CourseDB();
        try{
            List<Course> courses = cdb.findAllCourse();
            List<Integer> courseIds = courses
                    .stream()
                    .map(course ->  Integer.valueOf(course.getCourseID()))
                    .collect(Collectors.toList());
            return courseIds;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void loadCourse(){

    }

}
