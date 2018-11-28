package DAO;

import grades.Grade;
import grades.Tag;

import java.util.List;

public interface GradeDAO {
    public List<Grade> findOneStudentAllGradeInOneCourse(String studentid, int courseid) throws Exception;
    public Grade findOneGrade(String studentid, int gradableid) throws Exception;
    public void insertGrade(Grade g) throws Exception;
    public void addTagsToOneGrade(List<Tag> tlist, Grade g) throws Exception;
    public void updateGrade(Grade g) throws  Exception;
    public void deleteGrade(Grade g) throws Exception;
}
