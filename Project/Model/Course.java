package Database;

public class Course {
    private String courseID;
    private String courseName;
    private int courseSemester;
    private String courseDegree;
    private int courseDepartment;
    private int collegeID;
    Course(String courseID, String courseName, int courseSemester, int courseDepartment, int collegeID, String courseDegree) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseSemester = courseSemester;
        this.collegeID = collegeID;
        this.courseDegree = courseDegree;
        this.courseDepartment = courseDepartment;
    }
    public String getCourseID() {
        return courseID;
    }
    public void setCourseID(String courseID) {
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
    public String getCourseDegree() {
        return courseDegree;
    }
    public void setCourseDegree(String courseDegree) {
        this.courseDegree = courseDegree;
    }
    public void setCourseSemester(int courseSemester) {
        this.courseSemester = courseSemester;
    }
    public int getCourseDepartment() {
        return courseDepartment;
    }
    public void setCourseDepartment(int courseDepartment) {
        this.courseDepartment = courseDepartment;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
}
