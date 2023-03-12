package Model;

public class CourseProfessor{
    private int professorID;
    private int courseID;
    private int departmentID;
    private int collegeID;
    public CourseProfessor(int professorID, int courseID, int departmentID, int collegeID) {
        this.professorID = professorID;
        this.courseID = courseID;
        this.departmentID = departmentID;
        this.collegeID = collegeID;
    }
    public int getProfessorID() {
        return professorID;
    }
    public void setProfessorID(int professorID) {
        this.professorID = professorID;
    }
    public int getCourseID() {
        return courseID;
    }
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    public int getDepartmentID() {
        return departmentID;
    }
    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
}