package ui;
import java.util.ArrayList;
import java.util.List;


public class Category {
    public String categoryName;
    public String ugradWeight;
    public String gradWeight;
    public List<Assignment> assignmentList;

    public Category (String categoryName, String ugradWeight, String gradWeight, List<Assignment> assignmentList){
        this.categoryName = categoryName;
        this.ugradWeight = ugradWeight;
        this.gradWeight = gradWeight;
        this.assignmentList = assignmentList;
    }

    public List<Assignment> copyAssingments(){
        ArrayList<Assignment> assignments = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            Assignment newAssigment = new Assignment(assignment.name, assignment.totalPoints, assignment.totalPoints, new ArrayList<>());
            assignments.add(newAssigment);
        }
        return assignments;
    }

    public void addNewAssignment(String name, int totalPoints){
        Assignment assignment = new Assignment(name, totalPoints, totalPoints, new ArrayList<>());
        this.assignmentList.add(assignment);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUgradWeight() {
        return ugradWeight;
    }

    public void setUgradWeight(String ugradWeight) {
        this.ugradWeight = ugradWeight;
    }

    public String getGradWeight() {
        return gradWeight;
    }

    public void setGradWeight(String gradWeight) {
        this.gradWeight = gradWeight;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }
}
