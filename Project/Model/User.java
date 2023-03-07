package Model;

public class User{
    private int userID;
    private String userName;
    private String userContactNumber;
    private String userDOB;
    private String userGender;
    private String userAddress;
    private String uPassword;
    public User(int userID,String userName, String userContactNumber, String userDOB, String userGender, String userAddress, String userPassword){
        this.userID = userID;
        this.userName = userName;
        this.userContactNumber = userContactNumber;
        this.userDOB = userDOB;
        this.userGender = userGender;
        this.userAddress = userAddress;
        this.uPassword = userPassword;
    }
    public int getID(){
        return this.userID;
    }
    public String getName(){
        return this.userName;
    }
    public String getContactNumber(){
        return this.userContactNumber;
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
    public void setID(int userID) {
        this.userID = userID;
    }
    public void setName(String userName) {
        this.userName = userName;
    }
    public void setContactNumber(String userContactNumber) {
        this.userContactNumber = userContactNumber;
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
    public void setPassword(String userPassword) {
        this.uPassword = userPassword;
    }
}