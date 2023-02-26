package Model;

public class Records {
    private String studentID;
    private String courseID;
    private int sectionID;
    private int departmentID;
    private String professorID;
    private int collegeID;
    private int transactionID;
    private int internalMarks;
    private int externalMarks;
    private int attendance;
    private float cgpa;
    private String status;
    private int semCompleted;
    public Records(String studentID, String courseID, int sectonID, int departmentID, String professorID, int collegeID, int transactionID, int internalMarks,
            int externalMarks, int attendance, float cgpa, String status, int semCompleted) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.sectionID = sectonID;
        this.departmentID = departmentID;
        this.professorID = professorID;
        this.collegeID = collegeID;
        this.transactionID = transactionID;
        this.internalMarks = internalMarks;
        this.externalMarks = externalMarks;
        this.attendance = attendance;
        this.cgpa = cgpa;
        this.status = status;
        this.semCompleted = semCompleted;
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
    public int getSectionID() {
        return sectionID;
    }
    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }
    public int getDepartmentID() {
        return departmentID;
    }
    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
    public String getProfessorID() {
        return professorID;
    }
    public void setProfessorID(String professorID) {
        this.professorID = professorID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
    public int getTransactionID() {
        return transactionID;
    }
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
    public int getInternalMarks() {
        return internalMarks;
    }
    public void setInternalMarks(int internalMarks) {
        this.internalMarks = internalMarks;
    }
    public int getExternalMarks() {
        return externalMarks;
    }
    public void setExternalMarks(int externalMarks) {
        this.externalMarks = externalMarks;
    }
    public int getAttendance() {
        return attendance;
    }
    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
    public float getCgpa() {
        return cgpa;
    }
    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getSemCompleted() {
        return semCompleted;
    }
    public void setSemCompleted(int semCompleted) {
        this.semCompleted = semCompleted;
    }
}
