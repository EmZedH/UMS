package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserDelete;

import java.sql.SQLException;

import Logic.Interfaces.ModuleInterface;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminLoggedInUserDelete implements ModuleInterface{

    private int userChoice;

    private UserDAO userDAO;
    private int userID;

    public SuperAdminLoggedInUserDelete(UserDAO userDAO, int userID) {
        this.userDAO = userDAO;
        this.userID = userID;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     DisplayUtility.dialogWithHeaderDisplay("Warning", "Account to be deleted is currently logged in");
    //     this.userChoice = InputUtility.inputChoice("Confirm? (You will be logged out once deleted)",new String[]{"Confirm Delete","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Account to be deleted is currently logged in");
        this.userChoice = InputUtility.inputChoice("Confirm? (You will be logged out once deleted)",new String[]{"Confirm Delete","Back"});
        if(this.userChoice == 1){
            this.userDAO.deleteUser(this.userID);
            CommonUI.processSuccessDisplay();
            return;
        }
    }
    
}
