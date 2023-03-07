package Model;

public class Professor {
    private int professorID;
    private int departmentID;
    private int collegeID;
    public Professor(int professorID, int departmentID, int collegeID) {
        this.departmentID = departmentID;
        this.professorID = professorID;
        this.collegeID = collegeID;
    }
    public int getProfessorID() {
        return professorID;
    }
    public void setProfessorID(int professorID) {
        this.professorID = professorID;
    }
    public int getDepartmentID() {
        return departmentID;
    }
    // public void setDepartmentID(int departmentID) {
    //     this.departmentID = departmentID;
    // }
    public int getCollegeID() {
        return collegeID;
    }
    // public void setCollegeID(int collegeID) {
    //     this.collegeID = collegeID;
    // }
    
}
