package DAO;

import grades.Tag;

import java.util.List;

public interface TagDAO {
    public List<Tag> findAllTagInOneGrade(String sid, int gradableid) throws Exception;
    // find all tag in a grade
    public List<Tag> findAllTag() throws Exception;
    // find all tag
    public int insertTag(Tag t) throws Exception;
    // add a tag to database
    public void deleteTag(Tag t)throws Exception;
    // delete a tag
    public void updateGradable(Tag t)throws Exception;
    // update tag name
}
