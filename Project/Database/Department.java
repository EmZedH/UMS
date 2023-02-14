package Database;

public class Department {
    private int deptID;
    private String deptName;
    private int collegeID;
    public Department(int deptID, String deptName, int collegeID) {
        this.deptID = deptID;
        this.deptName = deptName;
        this.collegeID = collegeID;
    }
    public int getDeptID() {
        return deptID;
    }
    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }
    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
}
