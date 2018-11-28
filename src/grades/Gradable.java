package grades;

import java.math.BigDecimal;

public class Gradable {
    private String assignmentName;
    private int gID;
    private String courseID;
    private BigDecimal maxScore;
    private BigDecimal weight_ungrad;
    private BigDecimal weight_grad;
    public double receivedScore;
    private int costomized;
    private String type;
    //public double receivedScore;
    public Gradable(){}

    public Gradable (String courseID,int gradableId, String assignmentName, BigDecimal maxScore, BigDecimal weightU, BigDecimal weightG, int c, String t){
        this.courseID=courseID;
        this.gID=gradableId;
        this.assignmentName = assignmentName;
        this.maxScore = maxScore;
        this.weight_ungrad=weightU;
        this.weight_grad=weightG;
        this.costomized=c;
        this.type=t;
        //this.receivedScore = receivedScore;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public int getgID() {
        return gID;
    }

    public void setgID(int gID) {
        this.gID = gID;
    }

    public BigDecimal getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(BigDecimal maxScore) {
        this.maxScore = maxScore;
    }

    public BigDecimal getWeight_ungrad() {
        return weight_ungrad;
    }

    public void setWeight_ungrad(BigDecimal weight_ungrad) {
        this.weight_ungrad = weight_ungrad;
    }

    public BigDecimal getWeight_grad() {
        return weight_grad;
    }

    public void setWeight_grad(BigDecimal weight_grad) {
        this.weight_grad = weight_grad;
    }

    public double getReceivedScore() {
        return receivedScore;
    }

    public void setReceivedScore(double receivedScore) {
        this.receivedScore = receivedScore;
    }

    public int getCostomized() {
        return costomized;
    }

    public void setCostomized(int costomized) {
        this.costomized = costomized;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
