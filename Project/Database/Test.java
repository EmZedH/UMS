package Database;

public class Test {
    private int testID;
    private String sID;
    private String courseID;
    private int collegeID;
    private int testMark;
    public Test(int testID, String sID, String courseID, int collegeID, int testMark) {
        this.testID = testID;
        this.sID = sID;
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
    public String getsID() {
        return sID;
    }
    public void setsID(String sID) {
        this.sID = sID;
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
