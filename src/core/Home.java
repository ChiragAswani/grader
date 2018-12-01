package core;

import DAO.CourseDAO;
import DAO.HomeDAO;
import database.CourseDB;
import database.HomeDB;

import java.util.List;
import java.util.stream.Collectors;

public class Home {
    List<Course> courses;

    public Boolean login(String pass){
        // Try auth
        // if username and password match return true else false
        HomeDAO auth = new HomeDB();
        try{
            return auth.checkUserInDB(pass);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void changeLogin(String pass){
        HomeDAO auth = new HomeDB();
        try{
            auth.checkUserInDB(pass);
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
    public Course loadCourse(int courseId){
        CourseDAO cdb = new CourseDB();
        try{
           Course course = cdb.findOneCourse(courseId);
           return course;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
