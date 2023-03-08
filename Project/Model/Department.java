package Model;

public class Department {
    private int departmentID;
    private String departmentName;
    private int collegeID;
    public Department(int departmentID, String departmentName, int collegeID) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.collegeID = collegeID;
    }
    public int getDepartmentID() {
        return departmentID;
    }
    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public int getCollegeID() {
        return collegeID;
    }
}
