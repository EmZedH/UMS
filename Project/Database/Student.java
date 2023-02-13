package Database;

public class Student {
    private int sID;
    private int sem;
    private int year;
    private String degree;
    private float cgpa;
    private int secID;
    private int uID;
    private int collegeID;
    private int deptID;
    Student(int sID, int sem, int year, String degree,  float cgpa, int secID, int uID, int collegeID, int deptID) {
        this.sID = sID;
        this.sem = sem;
        this.year = year;
        this.degree = degree;
        this.cgpa = cgpa;
        this.secID = secID;
        this.uID = uID;
        this.collegeID = collegeID;
        this.deptID = deptID;
    }
    public int getsID() {
        return sID;
    }
    public void setsID(int sID) {
        this.sID = sID;
    }
    public int getSem() {
        return sem;
    }
    public void setSem(int sem) {
        this.sem = sem;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getDegree() {
        return degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public float getCgpa() {
        return cgpa;
    }
    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }
    public int getSecID() {
        return secID;
    }
    public void setSecID(int secID) {
        this.secID = secID;
    }
    public int getuID() {
        return uID;
    }
    public void setuID(int uID) {
        this.uID = uID;
    }
    public int getCollegeID() {
        return collegeID;
    }
    public void setCollegeID(int collegeID) {
        this.collegeID = collegeID;
    }
    public int getDeptID() {
        return deptID;
    }
    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }
}
