package Database;

public class SuperAdmin {
    private int saID;
    private int userID;
    public SuperAdmin(int saID, int userID) {
        this.saID = saID;
        this.userID = userID;
    }
    public int getSaID() {
        return saID;
    }
    public void setSaID(int saID) {
        this.saID = saID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
}
