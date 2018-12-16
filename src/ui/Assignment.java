package ui;

import grades.Tag;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class Assignment {

    public String name;
    public int totalPoints;
    public int pointsLost;
    public int score;
    public List<Tag> tags;

    public Assignment(String name, int totalPoints, int pointsLost, List<Tag> tags){
        this.name = name;
        this.totalPoints = totalPoints;
        this.pointsLost = pointsLost;
        this.score = totalPoints-pointsLost;
        this.tags = tags;
    }

    public void updatePointsLost(int pointsLost){
        this.pointsLost = pointsLost;
        this.score = totalPoints-pointsLost;
    }

}
