package Model;

public class CollegeAdmin {
    private int collegeAdminID;
    private int collegeID;
    public CollegeAdmin(int userID, int collegeID) {
        this.collegeID = collegeID;
        this.collegeAdminID = userID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
    public int getCollegeAdminID() {
        return collegeAdminID;
    }
    public void setCollegeAdminID(int userID) {
        this.collegeAdminID = userID;
    }
    
}
