package DAO;

import grades.Grade;
import grades.Tag;

import java.util.List;

public interface GradeDAO {
    public List<Grade> findOneStudentAllGradeInOneCourse(String studentid, int courseid) throws Exception;
    // find one student all grade in a course.
    public Grade findOneGrade(String studentid, int gradableid) throws Exception;
    // give studentid and gradableid return a grade
    public void insertGrade(Grade g) throws Exception;
    // add a grade to database.
    public void addTagsToOneGrade(List<Tag> tlist, Grade g) throws Exception;
    // add tags to a grade
    public void updateGrade(Grade g) throws  Exception;
    // update grade
    public void deleteGrade(Grade g) throws Exception;
    // delete grade
}
