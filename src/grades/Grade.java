package grades;

import java.math.BigDecimal;
import java.util.List;

public class Grade {
    private String sID;
    private int gID;
    private BigDecimal score;
    private BigDecimal weighting;
    private List<Tag> tList;
    public Grade(){}
    public Grade(String sID, int ID,BigDecimal s,BigDecimal w,List<Tag> t){
        this.sID=sID;
        this.gID=ID;
        this.score=s;
        this.weighting=w;
        this.tList=t;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public int getgID() {
        return gID;
    }

    public void setgID(int gID) {
        this.gID = gID;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getWeighting() {
        return weighting;
    }

    public void setWeighting(BigDecimal weighting) {
        this.weighting = weighting;
    }

    public List<Tag> gettList() {
        return tList;
    }

    public void settList(List<Tag> tList) {
        this.tList = tList;
    }
}
