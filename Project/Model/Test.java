package Model;

public class Test {
    private int testID;
    private int studentID;
    private int courseID;
    private int departmentID;
    private int collegeID;
    private int testMark;
    public Test(int testID, int studentID, int courseID, int departmentID, int collegeID, int testMark) {
        this.testID = testID;
        this.studentID = studentID;
        this.courseID = courseID;
        this.departmentID = departmentID;
        this.collegeID = collegeID;
        this.testMark = testMark;
    }
    public int getTestID() {
        return testID;
    }
    public void setTestID(int testID) {
        this.testID = testID;
    }
    public int getStudentID() {
        return studentID;
    }
    // public void setStudentID(int studentID) {
    //     this.studentID = studentID;
    // }
    public int getCourseID() {
        return courseID;
    }
    // public void setCourseID(int courseID) {
    //     this.courseID = courseID;
    // }
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
    public int getTestMark() {
        return testMark;
    }
    public void setTestMark(int testMark) {
        this.testMark = testMark;
    }
    
}
