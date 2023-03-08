package Model;

public class SuperAdmin {
    User user;
    public SuperAdmin(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
}
