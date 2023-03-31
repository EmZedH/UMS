package Logic.UserInput.UserInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModuleInterface;
import Model.DatabaseAccessObject.UserDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class NonExistingUserInput implements ReturnableModuleInterface{


    private boolean exitStatus = false;
    private int returnUserID;

    private UserDAO userDAO;

    public NonExistingUserInput(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnUserID = InputUtility.posInput("Enter the new User ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnUserID = InputUtility.posInput("Enter the new User ID");
        if(!this.userDAO.verifyUser(this.returnUserID)){
            this.exitStatus = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("User ID already exists. Please try again");
    }

    @Override
    public int returnValue() {
        return this.returnUserID;
    }
    
   
}
