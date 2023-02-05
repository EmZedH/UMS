package Database;

public class User{
    private int uID;
    private String uName;
    private String uAadhar;
    private String uDOB;
    private String uGender;
    private String uAddress;
    private String uCollegeName;
    private String uCollegeAddress;
    private String uCollegeTelephone;
    private String uPassword;
    User(int uID,String uName, String uAadhar, String uDOB, String uGender, String uAddress, String uCollegeName, String uCollegeAddress, String uCollegeTelephone, String uPassword){
        this.uID = uID;
        this.uName = uName;
        this.uAadhar = uAadhar;
        this.uDOB = uDOB;
        this.uGender = uGender;
        this.uAddress = uAddress;
        this.uCollegeName = uCollegeName;
        this.uCollegeAddress = uCollegeAddress;
        this.uCollegeTelephone = uCollegeTelephone;
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
    public String getCollegeName(){
        return this.uCollegeName;
    }
    public String getCollegeAddress(){
        return this.uCollegeAddress;
    }
    public String getCollegeTelephone(){
        return this.uCollegeTelephone;
    }
    public String getPassword(){
        return this.uPassword;
    }
}