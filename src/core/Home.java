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
            if(auth.hasLogin()){
                return auth.checkUserInDB(pass, pass);
            }
            else changeLogin(pass);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public void changeLogin(String pass){
        HomeDAO auth = new HomeDB();
        try{
            auth.checkUserInDB(pass, pass);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<String[]> seeCourses(){
        //Return list of tuples of courseID and courseName for all of users courses
        CourseDAO cdb = new CourseDB();
        try{
            List<Course> courses = cdb.findAllCourse();
            List<String[]> courseIds = courses
                    .stream()
                    .map(course ->  new String[] {Integer.toString(course.getCourseID()), course.getCourseName()})
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
           Course course = cdb.findOneCourse(Integer.toString(courseId));
           return course;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
