package DAO;

import grades.Gradable;

import java.util.List;

public interface GradableDAO {
    public List<Gradable> findAllGradableInCourse(String courseID) throws Exception;
    public Gradable findOneGradableInCourse(String courseID,int gradableid) throws Exception;
    public Gradable addGradableToOneCourse(Gradable g) throws Exception;
    public void removeGradableFromOneCourse(Gradable g) throws Exception;
    public int insertGradable(Gradable g) throws Exception;
    public void deleteGradable(Gradable g)throws Exception;
    public void updateGradable(Gradable g)throws Exception;
}
