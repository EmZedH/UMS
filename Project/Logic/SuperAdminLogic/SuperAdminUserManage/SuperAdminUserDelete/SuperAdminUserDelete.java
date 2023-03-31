package Logic.SuperAdminLogic.SuperAdminUserManage.SuperAdminUserDelete;

import java.sql.SQLException;

import Logic.Interfaces.ModuleInterface;
import Model.User;
import Model.DatabaseAccessObject.UserDAO;
import UI.CommonUI;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class SuperAdminUserDelete implements ModuleInterface{

    private int userChoice;

    private int userID;
    private UserDAO userDAO;

    public SuperAdminUserDelete(UserDAO userDAO, int userID) {
        this.userDAO = userDAO;
        this.userID = userID;
    }

    @Override
    public boolean getExitStatus() {
        return true;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     User user = this.userDAO.returnUser(this.userID);
    //     DisplayUtility.dialogWithHeaderDisplay("Warning", "Account ID: "+user.getID()+", Name: "+ user.getName() +" is selected for deletion");
    //     this.userChoice = InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm Delete","Back"});
    // }

    @Override
    public void runLogic() throws SQLException {
        User user = this.userDAO.returnUser(this.userID);
        DisplayUtility.dialogWithHeaderDisplay("Warning", "Account ID: "+user.getID()+", Name: "+ user.getName() +" is selected for deletion");
        this.userChoice = InputUtility.inputChoice("Confirm? (All data will be deleted)", new String[]{"Confirm Delete","Back"});
        if(this.userChoice == 1){
            this.userDAO.deleteUser(this.userID);
            CommonUI.processSuccessDisplay();
            return;
        }
    }
}