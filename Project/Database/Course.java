package Database;

public class Course {
    private String cID;
    private String cName;
    private int cSem;
    private String degree;
    private int cDept;
    private int collegeID;
    Course(String cID, String cName, int cSem, int cDept, int collegeID, String degree) {
        this.cID = cID;
        this.cName = cName;
        this.cSem = cSem;
        this.collegeID = collegeID;
        this.degree = degree;
        this.cDept = cDept;
    }
    public String getcID() {
        return cID;
    }
    public void setcID(String cID) {
        this.cID = cID;
    }
    public String getcName() {
        return cName;
    }
    public void setcName(String cName) {
        this.cName = cName;
    }
    public int getcSem() {
        return cSem;
    }
    public String getDegree() {
        return degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public void setcSem(int cSem) {
        this.cSem = cSem;
    }
    public int getcDept() {
        return cDept;
    }
    public void setcDept(int cDept) {
        this.cDept = cDept;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
}
