package Database;

public class Section {
    private int secID;
    private String secName;
    private int deptID;
    private int collegeID;
    public Section(int secID, String secName, int deptID, int collegeID) {
        this.secID = secID;
        this.secName = secName;
        this.deptID = deptID;
        this.collegeID = collegeID;
    }
    public int getSecID() {
        return secID;
    }
    public void setSecID(int secID) {
        this.secID = secID;
    }
    public String getSecName() {
        return secName;
    }
    public void setSecName(String secName) {
        this.secName = secName;
    }
    public int getDeptID() {
        return deptID;
    }
    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
}
