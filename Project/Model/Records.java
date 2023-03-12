package Model;

public class Records {
    private int studentID;
    private CourseProfessor courseProfessor;
    private int transactionID;
    private int externalMarks;
    private int attendance;
    private int assignmentMarks;
    private String status;
    private Integer semCompleted;
    public Records(int studentID, CourseProfessor courseProfessor, int transactionID, int externalMarks, int attendance,
            int assignmentMarks, String status, int semCompleted) {
        this.studentID = studentID;
        this.courseProfessor = courseProfessor;
        this.transactionID = transactionID;
        this.externalMarks = externalMarks;
        this.attendance = attendance;
        this.assignmentMarks = assignmentMarks;
        this.status = status;
        this.semCompleted = semCompleted;
    }
    public int getStudentID() {
        return studentID;
    }
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
    public CourseProfessor getCourseProfessor() {
        return courseProfessor;
    }
    public void setCourseProfessor(CourseProfessor courseProfessor) {
        this.courseProfessor = courseProfessor;
    }
    public int getTransactionID() {
        return transactionID;
    }
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
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
    public int getAssignmentMarks() {
        return assignmentMarks;
    }
    public void setAssignmentMarks(int assignmentMarks) {
        this.assignmentMarks = assignmentMarks;
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
    public void setSemCompleted(Integer semCompleted) {
        this.semCompleted = semCompleted;
    }
}
