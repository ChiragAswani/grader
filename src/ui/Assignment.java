package ui;

import javafx.beans.property.SimpleStringProperty;

public class Assignment {

    private final SimpleStringProperty assignment;

    public Assignment(String assignment) {
        this.assignment = new SimpleStringProperty(assignment);
    }

    public String getAssignment() {
        return assignment.get();
    }

    public void setAssignment(String assn) {
        assignment.set(assn);
    }


}
