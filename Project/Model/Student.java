package Model;

public class Student {
    private int studentID;
    private int semester;
    private String degree;
    private int sectionID;
    private int collegeID;
    private int departmentID;
    public Student(int userID, int semester, String degree, int sectionID, int collegeID, int departmentID) {
        this.semester = semester;
        this.degree = degree;
        this.sectionID = sectionID;
        this.studentID = userID;
        this.collegeID = collegeID;
        this.departmentID = departmentID;
    }
    public int getSemester() {
        return semester;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }
    public String getDegree() {
        return degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public int getSectionID() {
        return sectionID;
    }
    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }
    public int getStudentID() {
        return studentID;
    }
    public void setStudentID(int userID) {
        this.studentID = userID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    // public void setCollegeID(int collegeID) {
    //     this.collegeID = collegeID;
    // }
    public int getDepartmentID() {
        return departmentID;
    }
    // public void setDepartmentID(int departmentID) {
    //     this.departmentID = departmentID;
    // }
}
