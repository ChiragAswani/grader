package DAO;

import grades.Category;

import java.util.List;

public interface CategoryDAO {
    public void insertCategory(Category gc) throws Exception;
    public void deleteCategory(Category gc) throws Exception;
    public void updateCategory(Category gc) throws Exception;
    public List<Category> findAllCategoryInOneCourse(int courseid)throws Exception;
    public Category findOneCategoryInOneCourse(int courseid, String categoryName) throws Exception;

}
