package Logic.UserInput.UserInput;

import java.sql.SQLException;


import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.UserDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class ExistingUserInput implements ReturnableModule{

    private boolean canModuleExit = false;
    private int returnUserID;

    private UserDAO userDAO;

    public ExistingUserInput(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnUserID = InputUtility.posInput("Enter the User ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnUserID = InputUtility.posInput("Enter the User ID");
        if(this.userDAO.verifyUser(this.returnUserID)){
            this.canModuleExit = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("User ID doesn't exist. Please try again");
    }

    @Override
    public Integer returnValue() {
        return this.returnUserID;
    }
    
}
