package DAO;

import grades.Tag;

import java.util.List;

public interface TagDAO {
    public List<Tag> findAllTagInOneGrade(String sid, int gradableid) throws Exception;
    public List<Tag> findAllTag() throws Exception;
    public int insertTag(Tag t) throws Exception;
    public void deleteTag(Tag t)throws Exception;
    public void updateGradable(Tag t)throws Exception;
}
