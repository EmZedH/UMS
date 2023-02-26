package Model;

public class College {
    private int collegeID;
    private String collegeName;
    private String collegeAddress;
    private String collegeTelephone;
    public College(int collegeID, String collegeName, String collegeAddress, String collegeTelephone) {
        this.collegeID = collegeID;
        this.collegeName = collegeName;
        this.collegeAddress = collegeAddress;
        this.collegeTelephone = collegeTelephone;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
    public String getCollegeName() {
        return collegeName;
    }
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
    public String getCollegeAddress() {
        return collegeAddress;
    }
    public void setCollegeAddress(String cAddress) {
        this.collegeAddress = cAddress;
    }
    public String getCollegeTelephone() {
        return collegeTelephone;
    }
    public void setCollegeTelephone(String cTelephone) {
        this.collegeTelephone = cTelephone;
    }
}
