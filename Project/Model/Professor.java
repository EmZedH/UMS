package Model;

public class Professor {
    User user;
    Department department;
    public Professor(User user, Department department) {
        this.user = user;
        this.department = department;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Department getDepartment() {
        return department;
    }
    
}
