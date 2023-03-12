package Model;

public class Course {
    private int courseID;
    private String courseName;
    private int courseSemester;
    private String courseDegree;
    private int departmentID;
    private int collegeID;
    private String courseElective;
    public Course(int courseID, String courseName, int courseSemester, String courseDegree, int departmentID,
            int collegeID, String courseElective) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseSemester = courseSemester;
        this.courseDegree = courseDegree;
        this.departmentID = departmentID;
        this.collegeID = collegeID;
        this.courseElective = courseElective;
    }
    public int getCourseID() {
        return courseID;
    }
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public int getCourseSemester() {
        return courseSemester;
    }
    public void setCourseSemester(int courseSemester) {
        this.courseSemester = courseSemester;
    }
    public String getCourseDegree() {
        return courseDegree;
    }
    public void setCourseDegree(String courseDegree) {
        this.courseDegree = courseDegree;
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
    public String getCourseElective() {
        return courseElective;
    }
    public void setCourseElective(String courseElective) {
        this.courseElective = courseElective;
    }
}
