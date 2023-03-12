package Model;

public class Test {
    private int testID;
    private int studentID;
    private Course course;
    private int testMark;
    public Test(int testID, int studentID, Course course, int testMark) {
        this.testID = testID;
        this.studentID = studentID;
        this.course = course;
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
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public int getTestMark() {
        return testMark;
    }
    public void setTestMark(int testMark) {
        this.testMark = testMark;
    }
}
