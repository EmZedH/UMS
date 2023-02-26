package Model;

public class Department {
    private int departmentID;
    private String deptName;
    private int collegeID;
    public Department(int departmentID, String deptName, int collegeID) {
        this.departmentID = departmentID;
        this.deptName = deptName;
        this.collegeID = collegeID;
    }
    public int getDepartmentID() {
        return departmentID;
    }
    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
    public String getDeptName() {
        return deptName;
    }
    public void setDeptName(String departmentName) {
        this.deptName = departmentName;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
}
