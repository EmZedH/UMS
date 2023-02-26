package Model;

public class SuperAdmin {
    private int superAdminID;
    private int userID;
    public SuperAdmin(int superAdminID, int userID) {
        this.superAdminID = superAdminID;
        this.userID = userID;
    }
    public int getSuperAdminID() {
        return superAdminID;
    }
    public void setSuperAdminID(int superAdminID) {
        this.superAdminID = superAdminID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
}
