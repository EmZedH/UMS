package Model;

public class Test {
    private int testID;
    private String studentID;
    private String courseID;
    private int collegeID;
    private int testMark;
    public Test(int testID, String studentID, String courseID, int collegeID, int testMark) {
        this.testID = testID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.collegeID = collegeID;
        this.testMark = testMark;
    }
    public int getTestID() {
        return testID;
    }
    public void setTestID(int testID) {
        this.testID = testID;
    }
    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public String getCourseID() {
        return courseID;
    }
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
    public int getTestMark() {
        return testMark;
    }
    public void setTestMark(int testMark) {
        this.testMark = testMark;
    }
    
}
