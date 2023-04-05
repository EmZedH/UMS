package Logic.UserInput.UserInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModule;
import Model.DatabaseAccessObject.UserDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class NonExistingUserInput implements ReturnableModule{


    private boolean canModuleExit = false;
    private int returnUserID;

    private UserDAO userDAO;

    public NonExistingUserInput(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean canModuleExit() {
        return this.canModuleExit;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnUserID = InputUtility.posInput("Enter the new User ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnUserID = InputUtility.posInput("Enter the new User ID");
        if(!this.userDAO.verifyUser(this.returnUserID)){
            this.canModuleExit = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("User ID already exists. Please try again");
    }

    @Override
    public Integer returnValue() {
        return this.returnUserID;
    }
    
   
}
