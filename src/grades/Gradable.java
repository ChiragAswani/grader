package grades;

public class Gradable {
    public String assignmentName;
    public int gID;
    public double maxScore;
    public double receivedScore;

    public Gradable (String assignmentName, double maxScore, double receivedScore){
        this.assignmentName = assignmentName;
        this.maxScore = maxScore;
        this.receivedScore = receivedScore;
    }
}
