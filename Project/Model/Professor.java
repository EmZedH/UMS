package Model;

public class Professor {
    private String professorID;
    private int departmentID;
    private int userID;
    private int collegeID;
    public Professor(String professorID, int departmentID, int userID, int collegeID) {
        this.professorID = professorID;
        this.departmentID = departmentID;
        this.userID = userID;
        this.collegeID = collegeID;
    }
    public String getProfessorID() {
        return professorID;
    }
    public void setProfessorID(String professorID) {
        this.professorID = professorID;
    }
    public int getDepartmentID() {
        return departmentID;
    }
    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
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
