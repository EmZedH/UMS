package Database;

public class Transactions {
    private int transactionID;
    private String studentID;
    private int collegeID;
    private String date;
    private int amount;
    public Transactions(int transactionID, String studentID, int collegeID, String date, int amount) {
        this.transactionID = transactionID;
        this.studentID = studentID;
        this.collegeID = collegeID;
        this.date = date;
        this.amount = amount;
    }
    public int getTransactionID() {
        return transactionID;
    }
    public String getStudentID() {
        return studentID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public String getDate() {
        return date;
    }
    public int getAmount() {
        return amount;
    }
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
}
