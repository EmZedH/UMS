package Model;

public class User{
    private int userID;
    private String userName;
    private String userAadhar;
    private String userDOB;
    private String userGender;
    private String userAddress;
    private String userRole;
    private String uPassword;
    User(int userID,String userName, String userAadhar, String userDOB, String userGender, String userAddress, String userRole, String userPassword){
        this.userID = userID;
        this.userName = userName;
        this.userAadhar = userAadhar;
        this.userDOB = userDOB;
        this.userGender = userGender;
        this.userAddress = userAddress;
        this.userRole = userRole;
        this.uPassword = userPassword;
    }
    public int getID(){
        return this.userID;
    }
    public String getName(){
        return this.userName;
    }
    public String getAadhar(){
        return this.userAadhar;
    }
    public String getDOB(){
        return this.userDOB;
    }
    public String getGender(){
        return this.userGender;
    }
    public String getAddress(){
        return this.userAddress;
    }
    public String getPassword(){
        return this.uPassword;
    }
    public String getRole(){
        return this.userRole;
    }
    public void setID(int userID) {
        this.userID = userID;
    }
    public void setName(String userName) {
        this.userName = userName;
    }
    public void setAadhar(String userAadhar) {
        this.userAadhar = userAadhar;
    }
    public void setDOB(String userDOB) {
        this.userDOB = userDOB;
    }
    public void setGender(String userGender) {
        this.userGender = userGender;
    }
    public void setAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    public void setRole(String userRole) {
        this.userRole = userRole;
    }
    public void setPassword(String userPassword) {
        this.uPassword = userPassword;
    }
}