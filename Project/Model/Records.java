package Model;

public class Records {
    private int studentID;
    private int courseID;
    private int departmentID;
    private int professorID;
    private int collegeID;
    private int transactionID;
    // private int internalMarks;
    private int externalMarks;
    private int attendance;
    private int assignmentMarks;
    private String status;
    private int semCompleted;
    public Records(int studentID, int courseID, int departmentID, int professorID, int collegeID, int transactionID,
            int externalMarks, int attendance, int assignment, String status, int semCompleted) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.departmentID = departmentID;
        this.professorID = professorID;
        this.collegeID = collegeID;
        this.transactionID = transactionID;
        // this.internalMarks = internalMarks;
        this.externalMarks = externalMarks;
        this.attendance = attendance;
        this.assignmentMarks = assignment;
        this.status = status;
        this.semCompleted = semCompleted;
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
    public int getProfessorID() {
        return professorID;
    }
    public void setProfessorID(int professorID) {
        this.professorID = professorID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    // public void setCollegeID(int collegeID) {
    //     this.collegeID = collegeID;
    // }
    public int getTransactionID() {
        return transactionID;
    }
    // public void setTransactionID(int transactionID) {
    //     this.transactionID = transactionID;
    // }
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
    public int getAssignmentMarks() {
        return assignmentMarks;
    }
    public void setAssignmentMarks(int assignment) {
        this.assignmentMarks = assignment;
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
