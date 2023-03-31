package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserAdd;

import java.sql.SQLException;

import Logic.Interfaces.ModuleInterface;
import Model.SuperAdmin;
import Model.User;
import Model.DatabaseAccessObject.SuperAdminDAO;
import UI.CommonUI;
import UI.Utility.InputUtility;

public class SuperAdminSuperAdminAdd implements ModuleInterface{

    private SuperAdminDAO superAdminDAO;
    private int userID;

    private String userName;
    private String userContact;
    private String userDOB;
    private String userGender;
    private String userAddress;
    private String userPassword;

    public SuperAdminSuperAdminAdd(SuperAdminDAO superAdminDAO, int userID) {
        this.superAdminDAO = superAdminDAO;
        this.userID = userID;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.userName = InputUtility.inputString("Enter the Name");
    //     this.userContact = CommonUI.inputPhoneNumber("Enter the Contact number");
    //     this.userDOB = CommonUI.inputDate("Enter the Date of Birth");
    //     this.userGender = CommonUI.inputGender();
    //     this.userAddress = InputUtility.inputString("Enter the Address");
    //     this.userPassword = InputUtility.inputString("Enter the password");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.userName = InputUtility.inputString("Enter the Name");
        this.userContact = CommonUI.inputPhoneNumber("Enter the Contact number");
        this.userDOB = CommonUI.inputDate("Enter the Date of Birth");
        this.userGender = CommonUI.inputGender();
        this.userAddress = InputUtility.inputString("Enter the Address");
        this.userPassword = InputUtility.inputString("Enter the password");
        
        User user = new User(this.userID, this.userName, this.userContact, this.userDOB, this.userGender, this.userAddress, this.userPassword);
        
        //ADD SUPER ADMIN DETAILS TO DATABASE
        this.superAdminDAO.addSuperAdmin(new SuperAdmin(user));
    }

}