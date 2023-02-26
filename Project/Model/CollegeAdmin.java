package Database;

public class CollegeAdmin {
    private int collegeAdminID;
    private int collegeID;
    private int userID;
    public CollegeAdmin(int collegeAdminID, int collegeID, int userID) {
        this.collegeAdminID = collegeAdminID;
        this.collegeID = collegeID;
        this.userID = userID;
    }
    public int getCollegeAdminID() {
        return collegeAdminID;
    }
    public void setCollegeAdminID(int collegeAdminID) {
        this.collegeAdminID = collegeAdminID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
}
