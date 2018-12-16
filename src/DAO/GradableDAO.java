package DAO;

import grades.Category;
import grades.Gradable;

import java.util.List;

public interface GradableDAO {
    public List<Gradable> findAllGradableInCourse(int courseID) throws Exception;
    // give courseid return all gradable in course
    public Gradable findOneGradableInCourse(int courseID,int gradableid) throws Exception;
    // give courseid and gradableid return a gradable.
    public Gradable addGradableToOneCourse(Gradable g) throws Exception;
    // add a gradable to course
    public void removeGradableFromOneCourse(Gradable g) throws Exception;
    // remove a gradable from course
    public int insertGradable(Gradable g) throws Exception;
    // insert a gradable. unlike addGradableToOneCourse. it won't build connection between course and gradable
    public void deleteGradable(Gradable g)throws Exception;
    // delete a gradable. unlike removeGradableFromoneCourse. it will delete gradable in DB. So no course will have it any more.
    public void updateGradable(Gradable g)throws Exception;
    // update gradable.
    public List<Gradable> getAllGradableInOneCategory(Category c) throws Exception;
    // get
}
