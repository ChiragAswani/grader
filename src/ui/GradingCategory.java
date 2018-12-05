package ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Category {

    private final SimpleStringProperty gradingCategory;

    public Category(String gradingCategory) {
        this.gradingCategory = new SimpleStringProperty(gradingCategory);
    }

    public String getGradingCategory() {
        return gradingCategory.get();
    }


    public void setGradingCategory(String gc) {
        gradingCategory.set(gc);
    }

}
