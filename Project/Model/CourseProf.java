package Model;

public class CourseProf {
    private String professorID;
    private String courseID;
    private int departmentID;
    private int collegeID;
    public CourseProf(String professorID, String courseID, int departmentID, int collegeID) {
        this.professorID = professorID;
        this.courseID = courseID;
        this.departmentID = departmentID;
        this.collegeID = collegeID;
    }
    public String getProfessorID() {
        return professorID;
    }
    public void setProfessorID(String professorID) {
        this.professorID = professorID;
    }
    public String getCourseID() {
        return courseID;
    }
    public void setCourseID(String courseID) {
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
