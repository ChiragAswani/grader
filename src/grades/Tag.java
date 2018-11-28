package grades;

public class Tag {
    private int tID;
    private String tname;
    public Tag() {}
    public Tag(int id,String name){
        this.tID=id;
        this.tname=name;
    }

    public int gettID() {
        return tID;
    }

    public void settID(int tID) {
        this.tID = tID;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }
}
