package DAO;

import core.Course;

import java.util.List;

public interface CourseDAO {
    public List<Course> findAllCourse() throws Exception;
    // find all course in database .
    public Course findOneCourse(String courseID) throws Exception;
    // find one course by courseid
    public Course insertCourse(Course c) throws Exception;
    // insert one course into database. including all student and gradable in course
    public void deleteCourse(Course c) throws Exception;
    // delete course in database
    public void updateCourse(Course c) throws Exception;
    // update course course name only.

}
