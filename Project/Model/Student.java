package Model;

public class Student {
    private User user;
    private int semester;
    private String degree;
    private Section section;
    public Student(User user, int semester, String degree, Section section) {
        this.user = user;
        this.semester = semester;
        this.degree = degree;
        this.section = section;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getSemester() {
        return semester;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }
    public String getDegree() {
        return degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public Section getSection() {
        return section;
    }
    public void setSection(Section section) {
        this.section = section;
    }
}
