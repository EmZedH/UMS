package Model;

public class CourseProfessorTable {
    private String professorID;
    private String courseID;
    private int departmentID;
    private int collegeID;
    public CourseProfessorTable(String professorID, String courseID, int departmentID, int collegeID) {
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
    public int getDepartmentID() {
        return departmentID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    
}
