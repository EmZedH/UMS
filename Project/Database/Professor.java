package Database;

public class Professor {
    private int pID;
    private int deptID;
    private int userID;
    private int collegeID;
    public Professor(int pID, int deptID, int userID, int collegeID) {
        this.pID = pID;
        this.deptID = deptID;
        this.userID = userID;
        this.collegeID = collegeID;
    }
    public int getpID() {
        return pID;
    }
    public void setpID(int pID) {
        this.pID = pID;
    }
    public int getDeptID() {
        return deptID;
    }
    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
    
}
