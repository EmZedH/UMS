package Database;

public class Admin {
    private int adminID;
    private String aName;
    private int userID;
    Admin(int adminID, String aName, int userID){
        this.adminID = adminID;
        this.aName = aName;
        this.userID = userID;
    }
    public int getAdminID(){
        return this.adminID;
    }
    public String getName(){
        return this.aName;
    }
    public int getUserID(){
        return this.userID;
    }
}
