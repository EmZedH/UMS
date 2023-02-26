package Model;

public class Section {
    private int sectionID;
    private String sectionName;
    private int departmentID;
    private int collegeID;
    public Section(int sectionID, String sectionName, int departmentID, int collegeID) {
        this.sectionID = sectionID;
        this.sectionName = sectionName;
        this.departmentID = departmentID;
        this.collegeID = collegeID;
    }
    public int getSectionID() {
        return sectionID;
    }
    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }
    public String getSectionName() {
        return sectionName;
    }
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
    public int getDepartmentID() {
        return departmentID;
    }
    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
}
