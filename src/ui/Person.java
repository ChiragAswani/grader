package ui;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Person {

    public String firstName;
    public String lastName;
    public String BUID;
    public String type;
    public List<Category> categoryList;


    public Person(String firstName, String lastName, String BUID, List<Category> categoryList, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.BUID = BUID;
        this.categoryList = categoryList;
        this.type = type;
    }


    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void updateGrade(String categoryName, String assignmentName, int newLost){
        for (Category category:categoryList){
            if (category.getCategoryName().equals(categoryName)){
                for (Assignment assignment:category.getAssignmentList()){
                    if (assignment.name.equals(assignmentName)){
                        assignment.updatePointsLost(newLost);
                    }
                }
            }
        }
    }

    public List<Category> copyCategories(){
        ArrayList<Category> categories = new ArrayList<>();
        for (Category category : categoryList) {
            Category categoryCopy = new Category(category.categoryName, category.ugradWeight, category.gradWeight, category.copyAssingments());
            categories.add(categoryCopy);
        }
        return categories;
    }

    public void addCategory(String categoryName, String uWeight, String gWeight){
        Category newCategory = new Category(categoryName, uWeight, gWeight, new ArrayList<>());
        this.categoryList.add(newCategory);
    }

    public void addAssignment(String categoryName, String assignmentName, int maxScore){
        for (Category category:categoryList){
            if (category.getCategoryName().equals(categoryName)){
                category.addNewAssignment(assignmentName, maxScore);
            }
        }
    }

    public void setCategoryList(List<Category> categoryList1) {
        categoryList = categoryList1;
    }
}

