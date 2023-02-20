package Database;

public class College {
    private int cID;
    private String cName;
    private String cAddress;
    private String cTelephone;
    public College(int cID, String cName, String cAddress, String cTelephone) {
        this.cID = cID;
        this.cName = cName;
        this.cAddress = cAddress;
        this.cTelephone = cTelephone;
    }
    public int getcID() {
        return cID;
    }
    public void setcID(int cID) {
        this.cID = cID;
    }
    public String getcName() {
        return cName;
    }
    public void setcName(String cName) {
        this.cName = cName;
    }
    public String getcAddress() {
        return cAddress;
    }
    public void setcAddress(String cAddress) {
        this.cAddress = cAddress;
    }
    public String getcTelephone() {
        return cTelephone;
    }
    public void setcTelephone(String cTelephone) {
        this.cTelephone = cTelephone;
    }
}
