package core;

import DAO.CourseDAO;
import DAO.HomeDAO;
import database.CourseDB;
import database.HomeDB;

import java.util.List;
import java.util.stream.Collectors;

public class Home {
    List<Course> courses;
    private final static String DB_NAME = "oop_gradingsys";

    public Boolean login(String pass){
        // Check if DB is set up, if not then set it up
        // Try auth if password match return true else false

        initializeDatabase();

        HomeDAO auth = new HomeDB();
        try{
            if(auth.checkPasswordExist()){
                return auth.checkUserInDB(pass);
            }
            else {
                changeLogin(pass);
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public void changeLogin(String pass){
        HomeDAO auth = new HomeDB();
        try{
            auth.changePassword(pass);
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

    public Course loadCourse(String courseId){
        CourseDAO cdb = new CourseDB();
        try{
           Course course = cdb.findOneCourse(Integer.parseInt(courseId));
           return course;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Course createNewCourse(String courseName){
        Course newCourse = new Course();
        newCourse.setCourseName(courseName);
        CourseDAO cdb = new CourseDB();
        try{
            Course builtCourse = cdb.insertCourse(newCourse);
            return builtCourse;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Course cloneCourse(String courseName, String oldCourseId){
        Course newCourse = new Course();
        Course oldCourse = loadCourse(oldCourseId);
        newCourse.setCourseName(courseName);
        CourseDAO cdb = new CourseDB();
        try{
            Course builtCourse = cdb.insertCourse(newCourse);
            builtCourse.setGradableList(oldCourse.getGradableList());
            return builtCourse;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void initializeDatabase(){
        HomeDAO homeDB = new HomeDB();
        try{
            homeDB.checkDB(DB_NAME);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
