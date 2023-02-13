package Database;

public class User{
    private int uID;
    private String uName;
    private String uAadhar;
    private String uDOB;
    private String uGender;
    private String uAddress;
    private String uRole;
    private String uPassword;
    User(int uID,String uName, String uAadhar, String uDOB, String uGender, String uAddress, String uRole, String uPassword){
        this.uID = uID;
        this.uName = uName;
        this.uAadhar = uAadhar;
        this.uDOB = uDOB;
        this.uGender = uGender;
        this.uAddress = uAddress;
        this.uRole = uRole;
        this.uPassword = uPassword;
    }
    public int getID(){
        return this.uID;
    }
    public String getName(){
        return this.uName;
    }
    public String getAadhar(){
        return this.uAadhar;
    }
    public String getDOB(){
        return this.uDOB;
    }
    public String getGender(){
        return this.uGender;
    }
    public String getAddress(){
        return this.uAddress;
    }
    public String getPassword(){
        return this.uPassword;
    }
    public String getRole(){
        return this.uRole;
    }
    public void setID(int uID) {
        this.uID = uID;
    }
    public void setName(String uName) {
        this.uName = uName;
    }
    public void setAadhar(String uAadhar) {
        this.uAadhar = uAadhar;
    }
    public void setDOB(String uDOB) {
        this.uDOB = uDOB;
    }
    public void setGender(String uGender) {
        this.uGender = uGender;
    }
    public void setAddress(String uAddress) {
        this.uAddress = uAddress;
    }
    public void setRole(String uRole) {
        this.uRole = uRole;
    }
    public void setPassword(String uPassword) {
        this.uPassword = uPassword;
    }
}