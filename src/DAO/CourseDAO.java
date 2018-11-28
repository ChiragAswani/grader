package DAO;

import core.Course;

import java.util.List;

public interface CourseDAO {
    public List<Course> findAllCourse() throws Exception;
    public Course findOneCourse(String courseID) throws Exception;
    public Course insertCourse(Course c) throws Exception;
    public void deleteCourse(Course c) throws Exception;
    public void updateCourse(Course c) throws Exception;
}
