package Model;

public class CollegeAdmin {
    private User user;
    private College college;
    public CollegeAdmin(User user, College college) {
        this.user = user;
        this.college = college;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public College getCollege() {
        return college;
    }
    public void setCollege(College college) {
        this.college = college;
    }
    
}
