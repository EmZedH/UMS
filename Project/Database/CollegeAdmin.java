package Database;

public class CollegeAdmin {
    private int caID;
    private int collegeID;
    private int userID;
    public CollegeAdmin(int caID, int collegeID, int userID) {
        this.caID = caID;
        this.collegeID = collegeID;
        this.userID = userID;
    }
    public int getCaID() {
        return caID;
    }
    public void setCaID(int caID) {
        this.caID = caID;
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
