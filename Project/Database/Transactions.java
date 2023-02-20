package Database;

public class Transactions {
    private int tID;
    private String sID;
    private int collegeID;
    private String date;
    private int amount;
    public Transactions(int tID, String sID, int collegeID, String date, int amount) {
        this.tID = tID;
        this.sID = sID;
        this.collegeID = collegeID;
        this.date = date;
        this.amount = amount;
    }
    public int gettID() {
        return tID;
    }
    public String getsID() {
        return sID;
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
    public void settID(int tID) {
        this.tID = tID;
    }
    public void setsID(String sID) {
        this.sID = sID;
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
