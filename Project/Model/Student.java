package Model;

public class Student {
    private String studentID;
    private int semester;
    private int year;
    private String degree;
    private float cgpa;
    private int sectionID;
    private int userID;
    private int collegeID;
    private int departmentID;
    Student(String studentID, int semester, int year, String degree,  float cgpa, int sectionID, int userID, int collegeID, int departmentID) {
        this.studentID = studentID;
        this.semester = semester;
        this.year = year;
        this.degree = degree;
        this.cgpa = cgpa;
        this.sectionID = sectionID;
        this.userID = userID;
        this.collegeID = collegeID;
        this.departmentID = departmentID;
    }
    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public int getSemester() {
        return semester;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getDegree() {
        return degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public float getCgpa() {
        return cgpa;
    }
    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }
    public int getSectionID() {
        return sectionID;
    }
    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
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
    public int getDepartmentID() {
        return departmentID;
    }
    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
}
