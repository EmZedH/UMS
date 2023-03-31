package Logic.UserInput.CollegeInput;

import java.sql.SQLException;

import Logic.Interfaces.ReturnableModuleInterface;
import Model.DatabaseAccessObject.CollegeDAO;
import UI.Utility.DisplayUtility;
import UI.Utility.InputUtility;

public class NonExistingCollegeInput implements ReturnableModuleInterface{

    private CollegeDAO collegeDAO;

    private boolean exitStatus = false;
    private int returnCollegeID;

    public NonExistingCollegeInput(CollegeDAO collegeDAO) {
        this.collegeDAO = collegeDAO;
    }

    @Override
    public boolean getExitStatus() {
        return this.exitStatus;
    }

    // @Override
    // public void runUserInterface() throws SQLException {
    //     this.returnCollegeID = InputUtility.posInput("Enter the new College ID");
    // }

    @Override
    public void runLogic() throws SQLException {
        this.returnCollegeID = InputUtility.posInput("Enter the new College ID");
        if(!this.collegeDAO.verifyCollege(this.returnCollegeID)){
            this.exitStatus = true;
            return;
        }
        DisplayUtility.singleDialogDisplay("College ID already exists. Please try again");
    }

    @Override
    public int returnValue() {
        return this.returnCollegeID;
    }
    

    
}
